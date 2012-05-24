
import model.CotacaoDolar;
import model.CotacaoTituloPublico;
import model.enumerations.TipoTituloPublico;
import services.BovespaService;
import services.CambioService;
import services.TesouroDiretoService;
import sun.misc.BASE64Encoder;

public class Principal
{
	final String username = "marcelo";
	final String password = "senha";
	final String proxyHost = "192.168.1.4";
	final String proxyPort = "8080";
	String encodedLogin;
	
	public void setProxy(String username, String password, String proxyHost, String proxyPort)
	{
		final String userpass = username + ":" + password;
		
		//Configurações do proxy
        System.setProperty("proxySet", "true");

        //IP ou nome do servidor proxy
        System.setProperty("http.proxyHost", proxyHost);

        //Porta do proxy
        System.setProperty("http.proxyPort", proxyPort);
        
        //Encode das credencias de acesso ao proxy
        encodedLogin = new BASE64Encoder().encode(userpass.getBytes());
	}
	
	public static void main(String[] args)
	{
		TesouroDiretoService iTesouro = new TesouroDiretoService();
		CambioService iCambio = new CambioService();
		BovespaService iBovespa = new BovespaService();
		
//		List<Empresa> listaEmpresas = new ArrayList<Empresa>();
//		List<Acao> listaAcoes = new ArrayList<Acao>();
//		List<CotacaoTituloPublico> listaCotacoes = new ArrayList<CotacaoTituloPublico>();
//		List<CotacaoTituloPublico> listaCotacoesTituloPublico = new ArrayList<CotacaoTituloPublico>();
//		CotacaoTituloPublico cotacao = new CotacaoTituloPublico();
//		GregorianCalendar vencimento = new GregorianCalendar(2035,5-1,15);
//		TituloPublico t = new TituloPublico(vencimento, TipoTituloPublico.NTNB_PRINC, listaCotacoes);
//		List<TituloPublico> listaTitulosPublicos = new ArrayList<TituloPublico>();
		CotacaoDolar c = new CotacaoDolar();
		
		c = iCambio.recuperarUltimaCotacao();
		
//		listaTitulosPublicos = iTesouro.recuperarTitulosPublicos(TipoTituloPublico.LFT, 2012);
		
		//listaAcoes.add(new Acao("PETR4", new Empresa("Petrobrás")));
		//listaAcoes.add(new Acao("VALE5", new Empresa("Vale")));
		
		//listaEmpresas = iBovespa.recuperarEmpresasBovespa();
		//listaAcoes = iBovespa.recuperarAcoesEmpresa(9512);
		//listaCotacoes = iBovespa.recuperarCotacaoMultipla(listaAcoes);
		//listaCotacoesTituloPublico = iTesouro.recuperarCotacoes(TipoTituloPublico.NTNB_PRINC, 2012);
//		cotacao = iTesouro.recuperarUltimaCotacao(TipoTituloPublico.NTNB_PRINC);
		
//		for (Iterator<Empresa> i = listaEmpresas.iterator(); i.hasNext(); )
//		{  
//		    Empresa e = (Empresa) i.next();
//		 	System.out.println(e.toString());
//		}
		
//		for (Iterator<Acao> i = listaAcoes.iterator(); i.hasNext(); )
//		{  
//			Acao a = (Acao) i.next();
//		 	System.out.println(a.toString());
//		}
		
//		for (Iterator<Cotacao> i = listaCotacoes.iterator(); i.hasNext(); )
//		{  
//			Cotacao c = (Cotacao) i.next();
//		 	System.out.println(c.toString());
//		}

//		for (Iterator<CotacaoTituloPublico> i = listaCotacoesTituloPublico.iterator(); i.hasNext(); )
//		{  
//			CotacaoTituloPublico c = (CotacaoTituloPublico) i.next();
//		 	System.out.println(c.toString());
//		}
		
//		Iterator<TituloPublico> itr = listaTitulosPublicos.iterator();
//		
//		while(itr.hasNext())
//		{
//			TituloPublico tt = itr.next(); 
//		    System.out.print(tt + "\n");
//		}
		
		System.out.print(c + "\n");
		
		System.out.println("--- FIM ---");
    }
}