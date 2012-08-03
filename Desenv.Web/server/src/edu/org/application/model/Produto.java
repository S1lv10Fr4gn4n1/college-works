package edu.org.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_produto")
	private Long id;
	
	@Column(length=100, name="desc_produto", nullable=false)
	private String descricao;
	
	@Column(name="barras", nullable=true)
	private byte[] imgBarras;
	
	@Column(length=100, name="cod_barras", nullable=true)
	private String codBarras;
	
	@OneToOne 
	@JoinColumn(name="id_unidade") 
	private Unidade unidade;

	@OneToOne 
	@JoinColumn(name="id_medida") 
	private Medida medida;
	
	@OneToOne 
	@JoinColumn(name = "id_fabricante")
	private Fabricante fabricante;
	
	@OneToOne 
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@OneToOne 
	@JoinColumn(name = "id_sub_categoria")
	private SubCategoria subCategoria;
	
	public Produto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getImgBarras() {
		return imgBarras;
	}

	public void setImgBarras(byte[] imgBarras) {
		this.imgBarras = imgBarras;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}
}
