package br.com.fiap.challenge.diner.core.application.ports;

import br.com.fiap.challenge.diner.core.domain.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.status != 'F' " +
            "ORDER BY CASE p.status " +
            "WHEN 'P' THEN 1 " +
            "WHEN 'E' THEN 2 " +
            "WHEN 'R' THEN 3 END, p.data DESC")
    Page<Pedido> findPedidosByCustomOrder(Pageable pageable);

}
