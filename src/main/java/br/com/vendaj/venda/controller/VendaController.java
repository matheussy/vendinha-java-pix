package br.com.vendaj.venda.controller;


import br.com.vendaj.venda.dto.ProdutoDto;
import br.com.vendaj.venda.repositorys.CategoriaRepository;
import br.com.vendaj.venda.repositorys.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendaController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@RequestMapping("/")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/produtos")
	public List<ProdutoDto> produtos() {
		return ProdutoDto.getProdutosDto(produtoRepository.findAll());
	}

	@RequestMapping("/produto/{id}")
	public ProdutoDto produto(@PathVariable int id) {
		return new ProdutoDto(produtoRepository.findById(id).orElseThrow());
	}

	@RequestMapping("/produtos/categoria/{categoria}")
	public List<ProdutoDto> produtosCategoria(@PathVariable String categoria) {
		return ProdutoDto.getProdutosDto(produtoRepository.getByCategory(categoriaRepository.findById(categoria).orElseThrow()));
	}

	@RequestMapping("/produto/img/{id}")
	public String getProdutoImg(@PathVariable int id) {
		return produtoRepository.getImg(id);
	}
}
