package br.com.vendaj.venda.models;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class ProdutoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer ID;

	private String Nome;

	private double Preco;

	private String Descricao;

	@ManyToOne
	@JoinColumn(name = "categoria_nome")
	private CategoriaModel Categoria;

	private String img;

	public ProdutoModel() {
	}

	public CategoriaModel getCategoria() {
		return Categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.Categoria = categoria;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		this.Nome = nome;
	}

	public double getPreco() {
		return Preco;
	}

	public void setPreco(double preco) {
		this.Preco = preco;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		this.Descricao = descricao;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}