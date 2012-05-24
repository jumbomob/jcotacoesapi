package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

public class CotacaoTituloPublico implements Serializable
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
		currencyFormatter.setMaximumFractionDigits(2);
		currencyFormatter.setMinimumFractionDigits(2);
	}

	//******************** Atributos **********************
	
	private Date dataCotacao;
	private Float taxaCompra;
	private Float taxaVenda;
	private Float precoCompra;
	private Float precoVenda;
	private Float precoBase;
	
	//******************** Associações ********************
	
	private TituloPublico tituloPublico;
	
	//******************** Contrutores ********************
	
	public CotacaoTituloPublico(){}

	public CotacaoTituloPublico( Date dataCotacao,
						  		 Float taxaCompra,
						  		 Float taxaVenda,
						  		 Float precoCompra,
						  		 Float precoVenda,
						  		 Float precoBase,
						  		 TituloPublico tituloPublico)
	{
		this.dataCotacao = dataCotacao;
		this.taxaCompra = taxaCompra;
		this.taxaVenda = taxaVenda;
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
		this.precoBase = precoBase;
		this.tituloPublico = tituloPublico;
	}

	//**************** Métodos GET e SET dos atributos ****************
	
	@Column(name = "dataCotacao") 
	@Temporal(TemporalType.DATE)
	public Date getDataCotacao()
	{
		return dataCotacao;
	}
	public void setDataCotacao(Date dataCotacao)
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

	@Column(name = "precoCompra", nullable = false, precision = 6, scale = 2)
	public Float getPrecoCompra()
	{
		return precoCompra;
	}
	public void setPrecoCompra(Float precoCompra)
	{
		this.precoCompra = precoCompra;
	}

	@Column(name = "precoVenda", nullable = false, precision = 6, scale = 2)
	public Float getPrecoVenda()
	{
		return precoVenda;
	}
	public void setPrecoVenda(Float precoVenda)
	{
		this.precoVenda = precoVenda;
	}
	
	@Column(name = "precoBase", nullable = false, precision = 6, scale = 2)
	public Float getPrecoBase()
	{
		return precoBase;
	}
	public void setPrecoBase(Float precoBase)
	{
		this.precoBase = precoBase;
	}

	//**************** Métodos GET e SET das associações ****************
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tituloPublico", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.SAVE_UPDATE)
	public TituloPublico getTituloPublico()
	{
		return tituloPublico;
	}
	public void setTituloPublico(TituloPublico tituloPublico)
	{
		this.tituloPublico = tituloPublico;
	}

	//**************** Outros métodos ****************
	
	public String toString()
	{
		return 	"\nData: " + dateFormatter.format(this.getDataCotacao()) +
				"\nTaxa de compra: " + percentageFormatter.format(this.getTaxaCompra()) +
				"\nTaxa de venda: " + percentageFormatter.format(this.getTaxaVenda()) +
				"\nPreço de compra: " + currencyFormatter.format(this.getPrecoCompra()) + 
				"\nPreço de venda: " + currencyFormatter.format(this.getPrecoVenda()) + 
				"\nPreço base: " + currencyFormatter.format(this.getPrecoBase());
	}
}