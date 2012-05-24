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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//******************** Atributos **********************
	
	private Long id;
	private String razaoSocial;
	private String nomePregao;
	private Integer codigoCVM;
	private String codigoBovespa;
	private String segmentoBovespa;
	private String setorEconomico;
	private String subSetorEconomico;
	private String segmentoEconomico;
	
	//******************** Associações ********************
	
	private List<Papel> listaPapeis = new ArrayList<Papel>();
	
	//******************** Contrutores ********************
	
	public Empresa(){}
	
	public Empresa(	String nome,
					String nomePregao,
					Integer codigoCVM,
					String codigoBovespa,
					String segmentoBovespa,
					String setorEconomico,
					String subSetorEconomico,
					String segmentoEconomico,					
					List<Papel> listaPapeis)
	{
		this.razaoSocial = nome;
		this.nomePregao = nomePregao;
		this.codigoCVM = codigoCVM;
		this.codigoBovespa = codigoBovespa;
		this.segmentoBovespa = segmentoBovespa;
		this.setorEconomico = setorEconomico; 
		this.subSetorEconomico = subSetorEconomico; 
		this.segmentoEconomico = segmentoEconomico;
		this.listaPapeis = listaPapeis;
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
	
	@Column(name="razaoSocial", unique=true, nullable=false, length=255)
	public String getRazaoSocial()
	{
		return razaoSocial;
	}
	public void setRazaoSocial(String nome)
	{
		this.razaoSocial = nome;
	}

	@Column(name="nomePregao", unique=true, nullable=false, length=25)
	public String getNomePregao()
	{
		return nomePregao;
	}
	public void setNomePregao(String nomePregao)
	{
		this.nomePregao = nomePregao;
	}
	
	@Column(name="codigoCVM", unique=true, nullable=false, length=6)
	public Integer getCodigoCVM()
	{
		return codigoCVM;
	}
	public void setCodigoCVM(Integer codigoCVM)
	{
		this.codigoCVM = codigoCVM;
	}
	
	@Column(name="codigoBovespa", unique=true, nullable=true, length=5)
	public String getCodigoBovespa()
	{
		return codigoBovespa;
	}
	public void setCodigoBovespa(String codigoBovespa)
	{
		this.codigoBovespa = codigoBovespa;
	}
	
	@Column(name="segmentoBovespa", unique=false, nullable=true, length=5)
	public String getSegmento()
	{
		return segmentoBovespa;
	}
	public void setSegmento(String segmento)
	{
		this.segmentoBovespa = segmento;
	}
	
	@Column(name="setorEconomico", unique=false, nullable=false, length=55)
	public String getSetorEconomico()
	{
		return setorEconomico;
	}
	public void setSetorEconomico(String setorEconomico)
	{
		this.setorEconomico = setorEconomico;
	}

	@Column(name="subSetorEconomico", unique=false, nullable=false, length=55)
	public String getSubSetorEconomico()
	{
		return subSetorEconomico;
	}
	public void setSubSetorEconomico(String subSetorEconomico)
	{
		this.subSetorEconomico = subSetorEconomico;
	}

	@Column(name="segmentoEconomico", unique=false, nullable=false, length=55)
	public String getSegmentoEconomico()
	{
		return segmentoEconomico;
	}
	public void setSegmentoEconomico(String segmentoEconomico)
	{
		this.segmentoEconomico = segmentoEconomico;
	}
	
	//**************** Métodos GET e SET das associações ****************

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	public List<Papel> getListaPapeis()
	{
		return listaPapeis;
	}
	public void setListaPapeis(List<Papel> listaPapeis)
	{
		this.listaPapeis = listaPapeis;
	}

	//**************** Outros métodos ****************
	
	public String toString ()
	{
		return "Empresa: " + this.getRazaoSocial() + "Nome pregão: " + this.getNomePregao() + "Segmento: " + this.getSegmento();
	}
	
	@Transient
	public String getClassificacaoSetorial()
	{
		return this.getSetorEconomico() + "." + this.getSubSetorEconomico() + "." + this.getSegmentoEconomico();
	}
}
