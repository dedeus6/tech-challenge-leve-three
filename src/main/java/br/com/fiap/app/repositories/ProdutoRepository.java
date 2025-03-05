package br.com.fiap.app.repositories;

import br.com.fiap.app.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT a FROM Produto a WHERE a.categoria.id = :categoriaId")
    Page<Produto> findByCategoriaId(@Param("categoriaId") Long categoriaId, PageRequest pageable);

}
