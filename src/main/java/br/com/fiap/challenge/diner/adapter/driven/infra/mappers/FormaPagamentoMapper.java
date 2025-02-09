package br.com.fiap.challenge.diner.adapter.driven.infra.mappers;

import br.com.fiap.challenge.diner.adapter.driver.response.FormaPagamentoResponse;
import br.com.fiap.challenge.diner.core.domain.entities.FormaPagamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper {

    List<FormaPagamentoResponse> toResponseList(List<FormaPagamento> content);
}
