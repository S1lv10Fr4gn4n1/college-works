package edu.org.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Medida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_medida")
	private Long id;

	@Column(length=100, name="desc_medida", nullable=false)
	private String descricao;
	
	public Medida() {
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
