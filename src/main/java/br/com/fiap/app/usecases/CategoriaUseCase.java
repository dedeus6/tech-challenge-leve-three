package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.CategoriaMapper;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.CategoriaResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.webui.dtos.response.ProdutoResponse;
import br.com.fiap.app.repositories.CategoriaRepository;
import br.com.fiap.utils.Paginacao;
import br.com.fiap.webui.dtos.CategoriaDTO;
import br.com.fiap.app.entities.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.errors.Errors.CATEGORIA_NAO_EXISTE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class CategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaResponse cadastrarCategoria(CategoriaDTO categoria) {
        var entity = categoriaMapper.toEntity(categoria);
        var entityResponse = categoriaRepository.save(entity);

        return categoriaMapper.toCategoriaResponse(entityResponse);
    }

    public CategoriaResponse atualizaCategoria(Long id, CategoriaDTO categoria) {
        var entity = getCategoria(id);
        entity.setId(id);
        entity.setDescricao(categoria.getDescricao());
        Categoria entityResponse = categoriaRepository.save(entity);
        return categoriaMapper.toCategoriaResponse(entityResponse);
    }

    public void deletarCategoria(Long id) {
        var entity = getCategoria(id);
        categoriaRepository.delete(entity);
    }

    public CategoriaResponse buscarCategoriaById(Long id) {
        return categoriaMapper.toCategoriaResponse(getCategoria(id));
    }

    public PaginacaoResponse<ProdutoResponse> listarCategorias(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var produtos = categoriaRepository.findAll(pageable);
        List<CategoriaResponse> categoriaResponses = categoriaMapper.toResponseList(produtos.getContent());
        return new PaginacaoResponse<ProdutoResponse>().convertToResponse(new PageImpl(categoriaResponses, produtos.getPageable(), 0L));
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CATEGORIA_NAO_EXISTE, UNPROCESSABLE_ENTITY));
    }

}
