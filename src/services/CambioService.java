package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.CotacaoDolar;

/**
 * Classe de serviços para obtenção de cotações de cambio.
 * @author Marcelo Magalhães
 * @version 0.0.1
 * @since Alpha 1
*/
public class CambioService
{
	private String URL_CAMBIO;
	
	public CambioService()
	{
		URL_CAMBIO = "http://www4.bcb.gov.br/pec/taxas/batch/taxas.asp?id=txdolar";
	}

	public CotacaoDolar recuperarUltimaCotacao()
	{
		CotacaoDolar c = new CotacaoDolar();
		String linha = new String();
		
		Pattern padraoData = Pattern.compile("\\d\\d/\\d\\d/\\d\\d\\d\\d");
		Pattern padroCotacao = Pattern.compile("\\d,\\d\\d\\d");
		
        try
        {
        	URL url = new URL(URL_CAMBIO);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((linha = reader.readLine()) != null)
			{
				if (linha.isEmpty())
					continue;
				
				if (linha.startsWith("<!--conteudo-->"))
				{
				
					int i = 0;
					int j = 0;
					
					//loop para procura da data da cotação
					//data com 10 posições 99/99/9999 (8 dígitos + 2 barras)
					for(i = 0 ; i != (linha.length() - 10) ; i++)
					{
						String temp = (String) linha.subSequence(i, i + 10);
						
						Matcher pesquisa = padraoData.matcher(temp);
						
						if (pesquisa.matches())
						{
							c.setDataCotacao((GregorianCalendar)util.Util.strToCalendar(temp));
							break;
						}
					}
					
					//loop para procura da taxa de compra
					//taxa com 5 posições 9,999 (4 digitos + 1 vírgula)
					for(i = 0 ; i != (linha.length() - 5) ; i++)
					{
						String temp = (String) linha.subSequence(i, i + 5);
						
						Matcher pesquisa = padroCotacao.matcher(temp);
						
						if (pesquisa.matches())
						{
							c.setTaxaCompra(util.Util.strToFloat(temp.replace("," , ".")));
							break;
						}
					}
					
					//loop para procura da taxa de venda
					//taxa com 5 posições 9,999 (4 digitos + 1 vírgula)
					//(o indexador continua o mesmo da procura anterior devido a linha ser a mesma para ambas as taxas)
					for(j = i + 1 ; j != (linha.length() - 5) ; j++)
					{
						String temp = (String) linha.subSequence(j, j + 5);
						
						Matcher pesquisa = padroCotacao.matcher(temp);
						
						if (pesquisa.matches())
						{
							c.setTaxaVenda(util.Util.strToFloat(temp.replace("," , ".")));
							break;
						}
					}
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
        
		return c;
	}
	
	
}
