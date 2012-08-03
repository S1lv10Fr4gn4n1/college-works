package edu.org.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fabricante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_fabricante")
	private Long id;

	@Column(length=100, name="desc_fabricante", nullable=false)
	private String descricao;

	public Fabricante() {
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
}
