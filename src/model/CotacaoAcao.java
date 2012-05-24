package model;

import java.io.Serializable;
import java.util.Date;

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

public class CotacaoAcao implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//******************** Atributos ********************
	
	private Date data;
	private Float precoAbertura;
	private Float precoMinimo;
	private Float precoMaximo;
	private Float precoMedio;
	private Float precoAtual;
	
	//******************** Associações ********************

	private Papel papel;
	
	//******************** Contrutores ********************
	
	public CotacaoAcao(){}

	public CotacaoAcao(	Papel papel,
					Date data,
					Float precoAbertura,
					Float precoMinimo,
					Float precoMaximo,
					Float precoMedio,
					Float precoAtual )
	{
		this.papel = papel;
		this.data = data;
		this.precoAbertura = precoAbertura;
		this.precoMinimo = precoMinimo;
		this.precoMaximo = precoMaximo;
		this.precoMedio = precoMedio;
		this.precoAtual = precoAtual;
	}

	//**************** Métodos GET e SET dos atributos ****************

	@Column(name = "data") 
	@Temporal(TemporalType.DATE)
	public Date getDataHora()
	{
		return data;
	}
	public void setDataHora(Date dataHora)
	{
		this.data = dataHora;
	}

	@Column(name = "precoAbertura", nullable = false, precision = 6, scale = 2)
	public Float getPrecoAbertura()
	{
		return precoAbertura;
	}
	public void setPrecoAbertura(Float precoAbertura)
	{
		this.precoAbertura = precoAbertura;
	}

	@Column(name = "precoMinimo", nullable = false, precision = 6, scale = 2)
	public Float getPrecoMinimo()
	{
		return precoMinimo;
	}
	public void setPrecoMinimo(Float precoMinimo)
	{
		this.precoMinimo = precoMinimo;
	}

	@Column(name = "precoMaximo", nullable = false, precision = 6, scale = 2)
	public Float getPrecoMaximo()
	{
		return precoMaximo;
	}
	public void setPrecoMaximo(Float precoMaximo)
	{
		this.precoMaximo = precoMaximo;
	}

	@Column(name = "precoMedio", nullable = false, precision = 6, scale = 2)
	public Float getPrecoMedio()
	{
		return precoMedio;
	}
	public void setPrecoMedio(Float precoMedio)
	{
		this.precoMedio = precoMedio;
	}

	@Column(name = "precoAtual", nullable = false, precision = 6, scale = 2)
	public Float getPrecoAtual()
	{
		return precoAtual;
	}
	public void setPrecoAtual(Float precoAtual)
	{
		this.precoAtual = precoAtual;
	}

	//**************** Métodos GET e SET das associações ****************
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_papel", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Papel getAtivo()
	{
		return papel;
	}
	public void setAtivo(Papel papel)
	{
		this.papel = papel;
	}

	//**************** Outros métodos ****************
	
	public String toString()
	{
		return "Data/hora: " + this.getDataHora() + " preço abertura: R$ " + this.getPrecoAbertura() + " preço mínimo: R$ " + this.getPrecoMinimo() + " preço máximo: R$ " + this.getPrecoMaximo() + " preço médio: R$ " + this.getPrecoMedio() + " preço atual: R$ " + this.getPrecoAtual();
	}
}