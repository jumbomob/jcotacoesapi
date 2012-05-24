package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) 
@SequenceGenerator(name="SEQUENCIA", sequenceName="SEQ_PAPEL", allocationSize=1)
public abstract class Papel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//******************** Atributos **********************
	
	private Long id;
	
	//******************** Associações ********************
	
	private Empresa empresa;
	private List<CotacaoAcao> listaCotacoes = new ArrayList<CotacaoAcao>();
	
	//******************** Construtores ********************
	
	public Papel(){}
	
	public Papel(Empresa empresa, List<CotacaoAcao> listaCotacoes)
	{
		this.empresa = empresa;
		this.listaCotacoes = listaCotacoes;
	}

	//**************** Métodos GET e SET dos atributos ****************
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long getId()
	{
		return id;
	}
	public void setId(Long newVal)
	{
		id = newVal;
	}
	
	//**************** Métodos GET e SET das associações ****************
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empresa", insertable = true, updatable = true)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	@OneToMany(mappedBy = "papel", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public List<CotacaoAcao> getListaCotacoes()
	{
		return listaCotacoes;
	}
	public void setListaCotacoes(List<CotacaoAcao> listaCotacoes)
	{
		this.listaCotacoes = listaCotacoes;
	}

	//**************** Outros métodos ****************
	
	@Transient
	public String toString ()
	{
		return "Empresa: " + this.getEmpresa();
	}
}
