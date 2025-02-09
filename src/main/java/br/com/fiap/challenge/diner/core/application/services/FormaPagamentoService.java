package br.com.fiap.challenge.diner.core.application.services;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.FormaPagamentoMapper;
import br.com.fiap.challenge.diner.adapter.driver.response.ClienteResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.FormaPagamentoResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoResponse;
import br.com.fiap.challenge.diner.core.application.ports.FormaPagamentoRepository;
import br.com.fiap.challenge.diner.core.application.utils.Paginacao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private final FormaPagamentoMapper mapper;

    public PaginacaoResponse<FormaPagamentoResponse> listarFormaPagamentos(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var formaPagamentos = formaPagamentoRepository.findAll(pageable);
        List<FormaPagamentoResponse> formaPagamentoResponse = mapper.toResponseList(formaPagamentos.getContent());
        return new PaginacaoResponse<ClienteResponse>().convertToResponse(new PageImpl(formaPagamentoResponse, formaPagamentos.getPageable(), 0L));
    }
}
