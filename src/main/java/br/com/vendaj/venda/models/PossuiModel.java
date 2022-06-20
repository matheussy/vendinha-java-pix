package br.com.vendaj.venda.models;

import javax.persistence.*;

@Entity
@Table(name = "possui")
public class PossuiModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private ProdutoModel produto;

	@ManyToOne
	@JoinColumn(name = "venda_id")
	private VendaModel venda;

	private int Qtd;

	public VendaModel getVenda() {
		return venda;
	}

	public void setVenda(VendaModel venda) {
		this.venda = venda;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQtd() {
		return Qtd;
	}

	public void setQtd(int qtd) {
		this.Qtd = qtd;
	}
}