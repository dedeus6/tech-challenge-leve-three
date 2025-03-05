package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.ProdutoMapper;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.webui.dtos.response.ProdutoResponse;
import br.com.fiap.app.repositories.CategoriaRepository;
import br.com.fiap.app.repositories.ProdutoRepository;
import br.com.fiap.utils.Paginacao;
import br.com.fiap.webui.dtos.ProdutoDTO;
import br.com.fiap.app.entities.Categoria;
import br.com.fiap.app.entities.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.errors.Errors.CATEGORIA_NAO_EXISTE;
import static br.com.fiap.errors.Errors.PRODUTO_NAO_ENCONTRADO;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class ProdutoUseCase {

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
