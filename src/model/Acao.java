package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "acao")
public class Acao extends Papel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//******************** Atributos ********************
	
	private static final Integer codigoBDI = 2;
	private static final Integer tipoMercado = 10;
	
	private String codigoAcao;
	
	//******************** Associações ********************
	

	
	//******************** Contrutores ********************
	
	public Acao(){}
	
	public Acao(String codigoAcao, Empresa empresa, List<CotacaoAcao> listaCotacoes)
	{
		super(empresa, listaCotacoes);
		this.codigoAcao = codigoAcao.toUpperCase();
	}

	//**************** Métodos GET e SET dos atributos ****************

	@Transient
	public Integer getCodigoBDI()
	{	
		return codigoBDI;
	}
	
	@Transient
	public Integer getTipoMercado()
	{	
		return tipoMercado;
	}
	
	@Column(name="codigoAcao", unique=true, nullable=false, length=8)
	public String getCodigo()
	{	
		return codigoAcao;
	}
	public void setCodigo(String codigo)
	{
		this.codigoAcao = codigo.toUpperCase();
	}

	//**************** Métodos GET e SET das associações ****************
	


	//**************** Outros métodos ****************
	
	@Transient
	public String toString ()
	{
		return "Código ação: " + this.getCodigo();
	}
}
