package br.com.fiap.challenge.diner.core.application.services;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.ProdutoMapper;
import br.com.fiap.challenge.diner.adapter.driver.exception.BusinessException;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.ProdutoResponse;
import br.com.fiap.challenge.diner.core.application.ports.CategoriaRepository;
import br.com.fiap.challenge.diner.core.application.ports.ProdutoRepository;
import br.com.fiap.challenge.diner.core.application.utils.Paginacao;
import br.com.fiap.challenge.diner.core.domain.dto.ProdutoDTO;
import br.com.fiap.challenge.diner.core.domain.entities.Categoria;
import br.com.fiap.challenge.diner.core.domain.entities.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.challenge.diner.core.application.errors.Errors.CATEGORIA_NAO_EXISTE;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.PRODUTO_NAO_ENCONTRADO;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final CategoriaRepository categoriaRepository;

    public ProdutoResponse cadastrarProduto(ProdutoDTO produto) {
        var categoria = categoriaRepository.findById(produto.getCategoriaId());

        return categoria.map(c -> {
            var entidade = produtoMapper.toEntity(produto);
            entidade.setCategoria(categoria.get());
            var entityResponse = produtoRepository.save(entidade);

            return produtoMapper.toProdutoResponse(entityResponse);
        }).orElseThrow(() -> new BusinessException(CATEGORIA_NAO_EXISTE, UNPROCESSABLE_ENTITY));
    }

    public PaginacaoResponse<ProdutoResponse> listarProdutos(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var produtos = produtoRepository.findAll(pageable);
        List<ProdutoResponse> produtosResponse = produtoMapper.toResponseList(produtos.getContent());
        return new PaginacaoResponse<ProdutoResponse>().convertToResponse(new PageImpl(produtosResponse, produtos.getPageable(), 0L));
    }

    public ProdutoResponse atualizaProduto(Long id, ProdutoDTO produtoDTO) {
        getProduto(id);
        var categoria = getCategoria(produtoDTO.getCategoriaId());

        Produto entity = produtoMapper.toEntity(produtoDTO);
        entity.setId(id);
        entity.setCategoria(categoria);
        Produto entityResponse = produtoRepository.save(entity);
        return produtoMapper.toProdutoResponse(entityResponse);
    }

    public void deletaProduto(Long id) {
        var produto = getProduto(id);
        produtoRepository.delete(produto);
    }

    public ProdutoResponse buscarProdutoById(Long id) {
        return produtoMapper.toProdutoResponse(getProduto(id));
    }

    public PaginacaoResponse<ProdutoResponse> buscarProdutosPorCategoria(Integer page, Integer limit, String sort, Long categoriaId) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var produtos = produtoRepository.findByCategoriaId(categoriaId, pageable);
        List<ProdutoResponse> produtosResponse = produtoMapper.toResponseList(produtos.getContent());
        return new PaginacaoResponse<ProdutoResponse>().convertToResponse(new PageImpl(produtosResponse, produtos.getPageable(), 0L));
    }

    private Produto getProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PRODUTO_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CATEGORIA_NAO_EXISTE, UNPROCESSABLE_ENTITY));
    }

}
