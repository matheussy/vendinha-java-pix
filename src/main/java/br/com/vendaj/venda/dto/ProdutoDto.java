package br.com.vendaj.venda.dto;

import br.com.vendaj.venda.models.ProdutoModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDto {

	private Integer id;

	private String nome;

	private double valor;

	private String descricao;

	private String categoria;

	private String img;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public ProdutoDto(ProdutoModel produtoModel) {
		this.id = produtoModel.getId();
		this.nome = produtoModel.getNome();
		this.descricao = produtoModel.getDescricao();
		this.img = produtoModel.getImg();
		this.valor = produtoModel.getValor();
		this.categoria = produtoModel.getCategoria().getNome();
	}

	public static List<ProdutoDto> getProdutosDto (List<ProdutoModel> produtos) {
		return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}

}
