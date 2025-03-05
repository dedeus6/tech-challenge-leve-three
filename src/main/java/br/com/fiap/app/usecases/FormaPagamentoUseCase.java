package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.FormaPagamentoMapper;
import br.com.fiap.webui.dtos.response.ClienteResponse;
import br.com.fiap.webui.dtos.response.FormaPagamentoResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.app.repositories.FormaPagamentoRepository;
import br.com.fiap.utils.Paginacao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoUseCase {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private final FormaPagamentoMapper mapper;

    public PaginacaoResponse<FormaPagamentoResponse> listarFormaPagamentos(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var formaPagamentos = formaPagamentoRepository.findAll(pageable);
        List<FormaPagamentoResponse> formaPagamentoResponse = mapper.toResponseList(formaPagamentos.getContent());
        return new PaginacaoResponse<ClienteResponse>().convertToResponse(new PageImpl(formaPagamentoResponse, formaPagamentos.getPageable(), 0L));
    }
}
