package br.com.vendaj.venda.dto;

import br.com.vendaj.venda.models.PossuiModel;

import java.util.List;
import java.util.stream.Collectors;

public class PossuiDto {

	private int id;

	private int produto;

	private  int quantidade;

	private int venda;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getVenda() {
		return venda;
	}

	public void setVenda(int venda) {
		this.venda = venda;
	}

	public PossuiDto(PossuiModel possuiModel) {
		this.id = possuiModel.getId();
		this.produto = possuiModel.getProduto().getID();
		this.venda = possuiModel.getVenda().getId();
		this.quantidade = possuiModel.getQtd();
	}

	public static List<PossuiDto> getProdutosDto (List<PossuiModel> possui) {
		return possui.stream().map(PossuiDto::new).collect(Collectors.toList());
	}
}
