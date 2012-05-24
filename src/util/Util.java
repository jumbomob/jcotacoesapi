package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Util
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));

	static
	{
		nf.setMaximumFractionDigits (2);	   // O default � 3.
		nf.setMinimumFractionDigits (2);
		nf.setGroupingUsed(false);
	}

	public static boolean dataValida(String umaData)
	{
		try
		{
			if(umaData.length() != 10) return false;
		
			Integer.parseInt(umaData.substring(0,2));
			Integer.parseInt(umaData.substring(3,5));
			Integer.parseInt(umaData.substring(6,10));
		
			sdf.setLenient(false);
			sdf.parse(umaData);
			
			return true; 
		}
		catch(Exception e)
		{
			return false;
		} 
	}

	public static Date strToDate(String umaData)
	{
		int dia = Integer.parseInt(umaData.substring(0,2));
		int mes = Integer.parseInt(umaData.substring(3,5));
		int ano = Integer.parseInt(umaData.substring(6,10));

		return java.sql.Date.valueOf(ano + "-" + mes + "-" + dia);
	}

	public static Calendar strToCalendar(String umaData)
	{
		int dia = Integer.parseInt(umaData.substring(0, 2));
		int mes = Integer.parseInt(umaData.substring(3, 5));
		int ano = Integer.parseInt(umaData.substring(6, 10));

		Calendar data = new GregorianCalendar(ano, mes - 1, dia);
		
		return data;
	}
	
	public static Timestamp strToTimestamp(String umaData)
	{
		int dia = Integer.parseInt(umaData.substring(0,2));
		int mes = Integer.parseInt(umaData.substring(3,5));
		int ano = Integer.parseInt(umaData.substring(6,10));

		int hh = Integer.parseInt(umaData.substring(11,13));
		int mi = Integer.parseInt(umaData.substring(14, 16));
		int ss = Integer.parseInt(umaData.substring(17,19));

		return Timestamp.valueOf(ano + "-" + mes + "-" + dia + " " + hh + ":" + mi + ":" + ss);
	}

	public static String dateToStr(Date umaData)
	{
		return sdf.format(umaData);
	}

	public static String calendarToStr(Calendar umaData)
	{
		if (umaData == null)
			return "";
		else
			return sdf.format(umaData.getTime());
	}

	public static double strToDouble(String valor) throws NumberFormatException
	{
		if (valor == null || "".equals(valor))
		{
			return 0;
		}
		else   
		{	
			return Double.parseDouble(valor);
		}		
	}
	
	public static String doubleToStr(double valor)
	{
		return nf.format(valor);
	}
	
	public static float strToFloat(String valor) throws NumberFormatException
	{
		if (valor == null || "".equals(valor))
		{
			return 0;
		}
		else   
		{	
			return Float.parseFloat(valor);
		}		
	}
	
	public static String floatToStr(float valor)
	{
		return nf.format(valor);
	}
	
	public static File doDownload(String stringUrl, String pathLocal)
	{  
	    try {  
	      
	        //Encapsula a URL num objeto java.net.URL  
	        URL url = new URL(stringUrl);  
	          
	        //Cria streams de leitura (este metodo ja faz a conexao)...  
	        InputStream is = url.openStream();  
	  
	        //... e de escrita.  
	        FileOutputStream fos = new FileOutputStream(pathLocal);
	  
	        //Le e grava byte a byte. Voce pode (e deve) usar buffers para  
	        //melhor performance (BufferedReader).  
	        int umByte = 0;  
	        while ((umByte = is.read()) != -1)
	        {  
	            fos.write(umByte);  
	        }  
	  
	        //Nao se esqueca de sempre fechar as streams apos seu uso!  
	        is.close();  
	        fos.close();  
	  
	        //apos criar o arquivo fisico, retorna referencia para o mesmo
	        return new File(pathLocal); 
	          
	    }
	    catch (Exception e)
	    {  
	        //Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!  
	        //Aqui so vamos mostrar o stack no stderr.  
	        e.printStackTrace();  
	    }  
	         
	    return null;  
	}
	
	public static InputStream doDownload_2(String stringUrl)
	{  
	    try {  
	      
	        //Encapsula a URL num objeto java.net.URL  
	        URL url = new URL(stringUrl);  
	          
	        //Cria streams de leitura (este metodo ja faz a conexao)...  
	       InputStream is = url.openStream();
	       
	       return is;
	          
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();  
	    }  
	         
	    return null;  
	}
}