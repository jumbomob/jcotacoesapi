package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Acao;
import model.CotacaoAcao;
import model.Empresa;
import util.Util;

/**
 * Classe de serviços para obtenção de cotações de ações listadas na BOVESPA.
 * @author Marcelo Magalhães
 * @version 0.0.1
 * @since Alpha 1
*/
public class BovespaService
{	
	private String URL_LISTAR_EMPRESAS;
	private String URL_EMPRESA;
	private String URL_COTACAO_SIMPLES;
	private String URL_COTACAO_MULTIPLA; 
	
	public BovespaService()
	{
		URL_LISTAR_EMPRESAS = "http://www.bmfbovespa.com.br/pregao-online/ExecutaAcaoCotRapXSL.asp?intIdiomaXsl=3";
		URL_EMPRESA = "http://www.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM=";
		URL_COTACAO_SIMPLES = "http://www.bmfbovespa.com.br/cotacoes2000/formCotacoesMobile.asp?codsocemi=";
		URL_COTACAO_MULTIPLA = "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=";
	}
	
	public List<Empresa> recuperarEmpresasBovespa()
	{        
		List<Empresa> result = new ArrayList<Empresa>();
		
        try
        {
            URL url = new URL(URL_LISTAR_EMPRESAS);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			int tables = 0;
			List<String> source = new ArrayList<String>();
			String buffer = null;
			
			while ((buffer = reader.readLine()) != null)
			{
				if (buffer.startsWith("<table")) tables++;
				
				if (tables == 3)
				{
					if (buffer.startsWith("<font face=\"verdana\"")) source.add(buffer);
					if (buffer.startsWith("<td") && buffer.length() > 66) source.add(buffer);
					if (buffer.startsWith("</table>")) break;
				}
			}
			
			for (int i = 0; i < source.size(); i = i + 2)
			{
				String nome = source.get(i).substring(37, source.get(i).length() - 7);
				String codigo = source.get(i + 1).substring(72, source.get(i + 1).indexOf("&"));
				
				if (codigo == null || nome == null) continue;
				
				Empresa e = new Empresa();
				
				e.setNomePregao(nome);
				e.setCodigoBovespa(codigo);
				
				result.add(e);
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

	
	public List<Acao> recuperarAcoesEmpresa(Integer codigoCVM)
	{
		String complementoURL = codigoCVM.toString();
		
		String linha = new String();
		
		List<Acao> result = new ArrayList<Acao>();
		
        try
        {
        	URL url = new URL(URL_EMPRESA + complementoURL);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			System.out.println(reader.toString());
			
			while ((linha = reader.readLine()) != null)
			{
				linha = linha.trim();
				
				if (linha.startsWith("<a href=\"javascript:;\" class=\"LinkCodNeg\" onclick=\"javascript:VerificaCotacao('"))
				{
					Acao a = new Acao();
										
					result.add(a);
				}
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
	
	
	public CotacaoAcao recuperarCotacaoSimples(String codigoAcao)
	{
        try
        {
        	URL url = new URL(URL_COTACAO_SIMPLES);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			reader.readLine();
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
        
		return null;
	}
	
	
	public List<CotacaoAcao> recuperarCotacaoMultipla(List<Acao> listaAtivos)
	{	
		String complementoURL = new String();
		
		String linha = new String();
		
		List<CotacaoAcao> result = new ArrayList<CotacaoAcao>();
		
        try
        {
    		for (Iterator<Acao> i = listaAtivos.iterator(); i.hasNext(); )
    		{  
    			Acao a = (Acao) i.next();
    			
    			complementoURL = complementoURL + a.getCodigo();
    			complementoURL = complementoURL + "|";
    		}
        	
            URL url = new URL(URL_COTACAO_MULTIPLA + complementoURL);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while ((linha = reader.readLine()) != null)
			{
				linha = linha.trim();
				
				if (linha.startsWith("<Papel Codigo"))
				{
					String[] info = linha.split("\" ");	
					
					CotacaoAcao c = new CotacaoAcao();
					
					//c.setAtivo(new Acao(info[0].substring(info[0].indexOf("\"")).substring(1),info[1].substring(info[1].indexOf("\"")).substring(1)));
					c.setDataHora(Util.strToDate(info[3].substring(info[3].indexOf("\"")).substring(1)));
					c.setPrecoAbertura(new Float((info[4].substring(info[4].indexOf("\"")).substring(1)).replaceAll(",", ".")));
					c.setPrecoMinimo(new Float((info[5].substring(info[5].indexOf("\"")).substring(1)).replaceAll(",", ".")));
					c.setPrecoMaximo(new Float((info[6].substring(info[6].indexOf("\"")).substring(1)).replaceAll(",", ".")));
					c.setPrecoMedio(new Float((info[7].substring(info[7].indexOf("\"")).substring(1)).replaceAll(",", ".")));
					c.setPrecoAtual(new Float((info[8].substring(info[8].indexOf("\"")).substring(1)).replaceAll(",", ".")));
					
					result.add(c);
				}
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
}
