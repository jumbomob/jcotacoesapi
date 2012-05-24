package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import model.enumerations.TipoTituloPublico;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

public class TituloPublico
{
	private static final long serialVersionUID = 1L;
	
	static DateFormat dateFormatter = new SimpleDateFormat("ddMMyy");
	
	//******************** Atributos **********************
	
	private Long id;
	private GregorianCalendar vencimento;
	
	//******************** Enumerações **********************

	private TipoTituloPublico tipo;

	//******************** Associações ********************
	
	private List<CotacaoTituloPublico> listaCotacoes = new ArrayList<CotacaoTituloPublico>();
	
	//******************** Construtores ********************
	
	public TituloPublico(){}

	public TituloPublico(GregorianCalendar vencimento, TipoTituloPublico tipo, List<CotacaoTituloPublico> listaCotacoes)
	{
		this.vencimento = vencimento;
		this.tipo = tipo;
		this.listaCotacoes = listaCotacoes;
	}

	//**************** Métodos GET e SET dos atributos ****************

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA")
	@Column(name="id")
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	@Column(name = "vencimento") 
	@Temporal(TemporalType.DATE)
	public GregorianCalendar getVencimento()
	{
		return vencimento;
	}
	public void setVencimento(GregorianCalendar vencimento)
	{
		this.vencimento = vencimento;
	}
	
	//**************** Métodos GET e SET das enumerações ****************
	
	@Enumerated(EnumType.STRING)
	@Column(name="id_tipo")
	public TipoTituloPublico getTipo()
	{
		return tipo;
	}  
	public void setTipo(TipoTituloPublico tipo)
	{
		this.tipo = tipo;
	}

	//**************** Métodos GET e SET das associações ****************
	
	@OneToMany(mappedBy = "tituloPublico", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public List<CotacaoTituloPublico> getListaCotacoes()
	{
		return listaCotacoes;
	}
	public void setListaCotacoes(List<CotacaoTituloPublico> listaCotacoes)
	{
		this.listaCotacoes = listaCotacoes;
	}

	//**************** Outros métodos ****************
	
	@Transient  
	public String getNome()
	{
		return (this.tipo.getNome() + " " + dateFormatter.format(this.getVencimento().getTime()));
	}
	
	public String toString()
	{
		if (this.getListaCotacoes() == null)
		{
			return 	"\nNome: " + this.getNome() +
					"\nVencimento: " + util.Util.calendarToStr(this.getVencimento()) + 
					this.getTipo().toString();
		}
		else
		{
			return 	"\nNome: " + this.getNome() +
					"\nVencimento: " + util.Util.calendarToStr(this.getVencimento()) + 
					this.getTipo().toString() +
					this.getListaCotacoes().toString();
		}
	}

}
