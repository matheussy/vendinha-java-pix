package br.com.vendaj.venda.repositorys;

import br.com.vendaj.venda.models.CategoriaModel;
import br.com.vendaj.venda.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
	@Query("select p from ProdutoModel p where p.Categoria = :categoria")
	List<ProdutoModel> getByCategory(@Param("categoria") CategoriaModel categoria);

	@Query("select p.img from ProdutoModel p where p.id = :id")
	String getImg(@Param("id") int id);

}
