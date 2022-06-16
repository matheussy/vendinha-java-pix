package br.com.vendaj.venda.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class CategoriaModel {
	@Id
	@Column(name = "nome", nullable = false)
	private String nome;

	private String descricao;

	private String img;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}