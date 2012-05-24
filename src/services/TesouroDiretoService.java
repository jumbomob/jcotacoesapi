package services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.CotacaoTituloPublico;
import model.TituloPublico;
import model.enumerations.TipoTituloPublico;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Classe de serviços para obtenção de cotações de títulos públicos.
 * @author Marcelo Magalhães
 * @version 0.0.1
 * @since Alpha 1
*/
public class TesouroDiretoService
{	
	private String URL_TESOURO_DIRETO;
	
	/**
	 * Contrutor padrão, define a URL de acesso ao site do Tesouro Direto.
	*/
	public TesouroDiretoService()
	{
		URL_TESOURO_DIRETO = "http://www.tesouro.fazenda.gov.br/tesouro_direto/download/historico";
	}

	/**
	 * Método que retorna a útima cotação válida de um certo tipo de título publico.
	 * @param tipo Tipo do título público.
	 * @return Objeto cotaçõa de um título público.
	*/
	public CotacaoTituloPublico recuperarUltimaCotacao(TipoTituloPublico tipo)
	{
		Calendar dataHoje = GregorianCalendar.getInstance();
		
		CotacaoTituloPublico result = new CotacaoTituloPublico();
		
		try
		{
			InputStream is = CarregarArquivoCotacao (tipo.getNomeArquivo(), dataHoje.get(Calendar.YEAR));
			
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			
			TituloPublico t = new TituloPublico ();
			
			Boolean continua = new Boolean(true);
			Integer i = 2;
			HSSFRow currentRow = null;
			
			while(continua)
			{
				currentRow = worksheet.getRow(i);
				
				HSSFCell cellCheck = currentRow.getCell(1);
				
				if (cellCheck == null)
				{
					currentRow = worksheet.getRow(i-1);
					
					HSSFCell cellA = currentRow.getCell(0);
					Date dataCotacao = cellA.getDateCellValue();
					result.setDataCotacao(dataCotacao);
					
					HSSFCell cellB = currentRow.getCell(1);
					Float taxaCompra = (float) cellB.getNumericCellValue();
					result.setTaxaCompra(taxaCompra);
					
					HSSFCell cellC = currentRow.getCell(2);
					Float taxaVenda = (float) cellC.getNumericCellValue();
					result.setTaxaVenda(taxaVenda);
					
					HSSFCell cellD = currentRow.getCell(3);
					Float precoCompra = (float) cellD.getNumericCellValue();
					result.setPrecoCompra(precoCompra);
					
					HSSFCell cellE = currentRow.getCell(4);
					Float precoVenda = (float) cellE.getNumericCellValue();
					result.setPrecoVenda(precoVenda);
					
					HSSFCell cellF = currentRow.getCell(5);
					Float precoBase = (float) cellF.getNumericCellValue();
					result.setPrecoBase(precoBase);
					
					result.setTituloPublico(t);
					
					continua = false;
				}
				
				i = i + 1;
			}
		}
        catch (MalformedURLException e)
        {
        	System.out.println("Erro na formatação da URL de acesso!!!");
        	e.printStackTrace();
        }
        catch (IOException e)
        {
        	System.out.println("Erro na leitura da linha do stream!!!");
        	e.printStackTrace();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
		return result;
	}
	
	public List<CotacaoTituloPublico> recuperarCotacoes(TipoTituloPublico tipo, Integer ano)
	{
		List<CotacaoTituloPublico> result = new ArrayList<CotacaoTituloPublico>();
		
		try
		{	
			InputStream is = CarregarArquivoCotacao (tipo.getNomeArquivo(), ano);
			
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			
			TituloPublico t = new TituloPublico ();
			
			Integer i = 2;
			HSSFRow currentRow = worksheet.getRow(i);
			HSSFCell cellCheck = currentRow.getCell(1);
			
			while(cellCheck != null)
			{
				HSSFCell cellA = currentRow.getCell(0);
				Date dataCotacao = cellA.getDateCellValue();
				
				HSSFCell cellB = currentRow.getCell(1);
				Float taxaCompra = (float) cellB.getNumericCellValue();
				
				HSSFCell cellC = currentRow.getCell(2);
				Float taxaVenda = (float) cellC.getNumericCellValue();
				
				HSSFCell cellD = currentRow.getCell(3);
				Float precoCompra = (float) cellD.getNumericCellValue();
				
				HSSFCell cellE = currentRow.getCell(4);
				Float precoVenda = (float) cellE.getNumericCellValue();
				
				HSSFCell cellF = currentRow.getCell(5);
				Float precoBase = (float) cellF.getNumericCellValue();
				
	        	CotacaoTituloPublico c = new CotacaoTituloPublico(dataCotacao, taxaCompra, taxaVenda, precoCompra, precoVenda, precoBase, t);
				
	        	result.add(c);

				i = i + 1;
				
				currentRow = worksheet.getRow(i);
				cellCheck = currentRow.getCell(1);
			}
		}
        catch (MalformedURLException e)
        {
        	System.out.println("Erro na formatação da URL de acesso!!!");
        	e.printStackTrace();
        }
        catch (IOException e)
        {
        	System.out.println("Erro na leitura da linha do stream!!!");
        	e.printStackTrace();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
		return result;
	}
	
	public List<TituloPublico> recuperarTitulosPublicos(Integer ano)
	{
		DateFormat formatter = new SimpleDateFormat("ddMMyy");
		
		List<TituloPublico> result = new ArrayList<TituloPublico>();
		
		 for (TipoTituloPublico tipo : TipoTituloPublico.values())
		 {	 
			try
			{	
				InputStream is = CarregarArquivoCotacao (tipo.getNomeArquivo(), ano);
				
				HSSFWorkbook workbook = new HSSFWorkbook(is);
				
				for (int i = 0 ; i < workbook.getNumberOfSheets() ; i++)
				{
					String nome = workbook.getSheetAt(i).getSheetName();
					
					GregorianCalendar vencimento = (GregorianCalendar) Calendar.getInstance();
					
					vencimento.setTime((Date)formatter.parse(nome.substring(nome.length() - 6,nome.length())));
					
					TituloPublico tp = new TituloPublico(vencimento, tipo, null);
					
					result.add(tp);
				}
			}
	        catch (MalformedURLException e)
	        {
	        	System.out.println("Erro na formatação da URL de acesso!!!");
	        	e.printStackTrace();
	        }
	        catch (IOException e)
	        {
	        	System.out.println("Erro na leitura da linha do stream!!!");
	        	e.printStackTrace();
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        }
		 }
		
		return result;
	}
	
	public List<TituloPublico> recuperarTitulosPublicos(TipoTituloPublico tipo, Integer ano)
	{
		DateFormat formatter = new SimpleDateFormat("ddMMyy");
		
		List<TituloPublico> result = new ArrayList<TituloPublico>();
		 
		try
		{	
			InputStream is = CarregarArquivoCotacao (tipo.getNomeArquivo(), ano);
			
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			
			for (int i = 0 ; i < workbook.getNumberOfSheets() ; i++)
			{
				String nome = workbook.getSheetAt(i).getSheetName();
				
				GregorianCalendar vencimento = (GregorianCalendar) Calendar.getInstance();
				
				vencimento.setTime((Date)formatter.parse(nome.substring(nome.length() - 6,nome.length())));
				
				TituloPublico tp = new TituloPublico(vencimento, tipo, null);
				
				result.add(tp);
			}
		}
        catch (MalformedURLException e)
        {
        	System.out.println("Erro na formatação da URL de acesso!!!");
        	e.printStackTrace();
        }
        catch (IOException e)
        {
        	System.out.println("Erro na leitura da linha do stream!!!");
        	e.printStackTrace();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
		
		return result;
	}
	
	private InputStream CarregarArquivoCotacao (String nomeArquivo, Integer ano)
	{
		String complementoURL = "/" + ano.toString() + "/" + "historico" + nomeArquivo + "_" + ano.toString() + ".xls";
		
	    try
	    {
	    	URL url = new URL(URL_TESOURO_DIRETO + complementoURL);
	    	
	    	InputStream is = url.openStream();
	    	
	    	return is;
	    }
        catch (MalformedURLException e)
        {
        	System.out.println("Erro na formatação da URL de acesso!!!");
        	e.printStackTrace();
        }
        catch (IOException e)
        {
        	System.out.println("Erro na abertura da URL de acesso!!!");
        	e.printStackTrace();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        return null;
	}

}
