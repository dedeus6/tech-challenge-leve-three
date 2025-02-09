package br.com.fiap.challenge.diner.core.application.ports;

import br.com.fiap.challenge.diner.core.domain.entities.PedidoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoPagamentoRepository extends JpaRepository<PedidoPagamento, Long> {

    Optional<PedidoPagamento> findByIdentificadorPagamento(String identificadorPagamento);

}
