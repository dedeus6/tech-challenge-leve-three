package br.com.fiap.app.repositories;

import br.com.fiap.app.entities.PedidoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoPagamentoRepository extends JpaRepository<PedidoPagamento, Long> {

    Optional<PedidoPagamento> findByIdentificadorPagamento(String identificadorPagamento);

}
