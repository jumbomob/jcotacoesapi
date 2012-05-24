package model.enumerations;

import java.util.GregorianCalendar;

public enum TipoTituloPublico
{
	//******************** Enumeração *********************
	
	LFT("LFT","Pós-fixado","Pêmio/Nominal","Selic","01/07/2000","N/A","LFT","LFT"),
	LTN("LTN","Prefixado","Nominal","N/A","N/A","N/A","LTN","LTN"),	
	NTNB("NTN-B","Pós-fixado","Real","IPCA","15/07/20000","6.00","NTNB","NTNB"),
	NTNB_PRINC("NTNB Principal","Pós-fixado","Real","IPCA","15/07/2000","N/A","NTNBPrincipal","NTNBP"),
	NTNC("NTN-C","Pós-fixado","Real","IGP-M","01/07/2000","6.00","NTNC","NTNC"),
	NTNF("NTN-F","Prefixado","Nominal","N/A","N/A","10.00","NTNF","NTNF");

	//******************** Atributos **********************
	
	private final String nome;
	private final String tipo;
	private final String taxaJuros;
	private final String indexador;
	private final String dataBase;
	private final String cupomAnual;
	private final String nomeArquivo;
	private final String nomePlanilha;

	//******************** Construtor **********************
	
	TipoTituloPublico(String nome, String tipo, String taxaJuros, String indexador, String dataBase, String cupomAnual, String nomeArquivo, String nomePlanilha)
	{
		this.nome = nome;
		this.tipo = tipo;
		this.taxaJuros = taxaJuros;
		this.indexador = indexador;
		this.dataBase = dataBase;
		this.cupomAnual = cupomAnual;
		this.nomeArquivo = nomeArquivo;
		this.nomePlanilha = nomePlanilha;
	}

	//**************** Outros métodos ****************
	
	public static TipoTituloPublico fromValue(String value)
	{
		if (value != null)
		{
			for (TipoTituloPublico tipo : values())
			{
				if (tipo.equals(value))
				{
					return tipo;
				}  
			}  
		}  
		throw new IllegalArgumentException("Enumeração inválida: " + value);  
	}
	
	public String getNome()
	{  
		return this.nome;
	}
	
	public String getTipo()
	{  
		return this.tipo;
	}
	
	public String getTaxaJuros()
	{
		return this.taxaJuros;
	}
	
	public String getIndexador()
	{
		if (this.indexador.equals("N/A"))
		{
			return null;
		}
		
		return this.indexador;
	}
	
	public GregorianCalendar getDataBase()
	{
		if (this.dataBase.equals("N/A"))
		{
			return null;
		}
		
		return (GregorianCalendar) util.Util.strToCalendar(this.dataBase);
	}
	
	public Float getCupomAnual()
	{
		if (this.cupomAnual.equals("N/A"))
		{
			return null;
		}
		
		return Float.parseFloat(this.cupomAnual);
	}
	
	public String getNomeArquivo()
	{  
		return this.nomeArquivo;
	}
	
	public String getNomePlanilha()
	{  
		return this.nomePlanilha;
	}
	
	public String toString()
	{
		return 	"\nNome do tipo: " + this.getNome() +
				"\nTipo: " + this.getTipo() + 
				"\nTaxa de juros: " + this.getTaxaJuros() +
				"\nIndexador: " + this.getIndexador() +
				"\nDate base: " + util.Util.calendarToStr(this.getDataBase()) +
				"\nCupom Anual: " + this.getCupomAnual() +
				"\nNome arquivo: " + this.getNomeArquivo() +
				"\nNome planilha: " + this.getNomePlanilha();
	}
	
}
