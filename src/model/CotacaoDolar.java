package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CotacaoDolar implements Serializable
{
private static final long serialVersionUID = 1L;
	
	static Locale locale;
	static DateFormat dateFormatter;
	static NumberFormat percentageFormatter;
	static NumberFormat currencyFormatter;
	
	static
	{
		locale = Locale.getDefault();
		
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		
		percentageFormatter = NumberFormat.getPercentInstance(locale);
		percentageFormatter.setMaximumFractionDigits(2);
		percentageFormatter.setMinimumFractionDigits(2);
		
		currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		currencyFormatter.setMaximumFractionDigits(3);
		currencyFormatter.setMinimumFractionDigits(3);
	}

	//******************** Atributos **********************
	
	private GregorianCalendar dataCotacao;
	private Float taxaCompra;
	private Float taxaVenda;
	
	//******************** Associações ********************
	

	
	//******************** Contrutores ********************
	
	public CotacaoDolar(){}

	public CotacaoDolar( GregorianCalendar dataCotacao, Float taxaCompra, Float taxaVenda)
	{
		this.dataCotacao = dataCotacao;
		this.taxaCompra = taxaCompra;
		this.taxaVenda = taxaVenda;
	}

	//**************** Métodos GET e SET dos atributos ****************
	
	@Column(name = "dataCotacao") 
	@Temporal(TemporalType.DATE)
	public GregorianCalendar getDataCotacao()
	{
		return dataCotacao;
	}
	public void setDataCotacao(GregorianCalendar dataCotacao)
	{
		this.dataCotacao = dataCotacao;
	}

	@Column(name = "taxaCompra", nullable = false, precision = 4, scale = 2)
	public Float getTaxaCompra()
	{
		return taxaCompra;
	}
	public void setTaxaCompra(Float taxaCompra)
	{
		this.taxaCompra = taxaCompra;
	}

	@Column(name = "taxaVenda", nullable = false, precision = 4, scale = 2)
	public Float getTaxaVenda()
	{
		return taxaVenda;
	}
	public void setTaxaVenda(Float taxaVenda)
	{
		this.taxaVenda = taxaVenda;
	}

	//**************** Métodos GET e SET das associações ****************
	


	//**************** Outros métodos ****************
	
	public String toString()
	{
		return 	"\nData: " + util.Util.calendarToStr(this.getDataCotacao()) +
				"\nTaxa de compra: " + currencyFormatter.format(this.getTaxaCompra()) +
				"\nTaxa de venda: " + currencyFormatter.format(this.getTaxaVenda());
	}

}
