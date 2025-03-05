package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.EmpresaMapper;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.EmpresaResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.utils.Paginacao;
import br.com.fiap.app.repositories.EmpresaRepository;
import br.com.fiap.webui.dtos.EmpresaDTO;
import br.com.fiap.app.entities.Empresa;
import br.com.fiap.enums.Ativo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.fiap.errors.Errors.EMPRESA_JA_CADASTRADA;
import static br.com.fiap.errors.Errors.EMPRESA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class EmpresaServiceUseCase {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper mapper;

    public EmpresaResponse cadastrarEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = empresaRepository.findByCnpj(empresaDTO.getCnpj());
        if (Objects.nonNull(empresa))
            throw new BusinessException(EMPRESA_JA_CADASTRADA, HttpStatus.UNPROCESSABLE_ENTITY);

        Empresa entity = mapper.toEntity(empresaDTO);
        entity.setAtivo(Ativo.N.name());
        Empresa entityResponse = empresaRepository.save(entity);
        return mapper.toEmpresaResponse(entityResponse);
    }

    public EmpresaResponse buscarEmpresaById(Long id) {
        var entity = empresaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(EMPRESA_NAO_ENCONTRADA, HttpStatus.UNPROCESSABLE_ENTITY));
        return mapper.toEmpresaResponse(entity);
    }

    public EmpresaResponse atualizarEmpresaById(Long id, EmpresaDTO empresaDTO) {
        empresaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(EMPRESA_NAO_ENCONTRADA, HttpStatus.UNPROCESSABLE_ENTITY));

        Empresa entity = mapper.toEntity(empresaDTO);
        entity.setId(id);
        Empresa entityResponse = empresaRepository.save(entity);
        return mapper.toEmpresaResponse(entityResponse);
    }

    public PaginacaoResponse<EmpresaResponse> listarEmpresas(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var empresas = empresaRepository.findAll(pageable);
        List<EmpresaResponse> empresasResponse = mapper.toResponseList(empresas.getContent());
        return new PaginacaoResponse<List<EmpresaResponse>>().convertToResponse(new PageImpl(empresasResponse, empresas.getPageable(), 0L));
    }
}
