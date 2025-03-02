package br.com.fiap.webui.mappers;

import br.com.fiap.webui.dtos.response.FormaPagamentoResponse;
import br.com.fiap.app.entities.FormaPagamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper {

    List<FormaPagamentoResponse> toResponseList(List<FormaPagamento> content);
}
