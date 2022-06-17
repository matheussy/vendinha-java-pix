package br.com.vendaj.venda.repositorys;

import br.com.vendaj.venda.models.CategoriaModel;
import br.com.vendaj.venda.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, String> {
}

