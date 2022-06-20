package br.com.vendaj.venda.dto;

import br.com.vendaj.venda.models.ProdutoModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDto {

	private Integer ID;

	private String Nome;

	private double Preco;

	private String Descricao;

	private String Categoria;

	private String IMG;

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

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		this.Categoria = categoria;
	}

	public String getIMG() {
		return IMG;
	}

	public void setIMG(String IMG) {
		this.IMG = IMG;
	}

	public ProdutoDto(ProdutoModel produtoModel) {
		this.ID = produtoModel.getID();
		this.Nome = produtoModel.getNome();
		this.Descricao = produtoModel.getDescricao();
		this.IMG = produtoModel.getImg();
		this.Preco = produtoModel.getPreco();
		this.Categoria = produtoModel.getCategoria().getNome();
	}

	public static List<ProdutoDto> getProdutosDto (List<ProdutoModel> produtos) {
		return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}

}
