package br.com.vendaj.venda.repositorys;

import br.com.vendaj.venda.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
