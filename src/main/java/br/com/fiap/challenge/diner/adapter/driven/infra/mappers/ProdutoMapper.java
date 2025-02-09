package br.com.fiap.challenge.diner.adapter.driven.infra.mappers;

import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarProdutoRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarProdutoRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.ProdutoResponse;
import br.com.fiap.challenge.diner.core.domain.dto.ProdutoDTO;
import br.com.fiap.challenge.diner.core.domain.entities.Produto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDTO toProdutoDto(CadastrarProdutoRequest request);
    ProdutoDTO toProdutoDto(AtualizarProdutoRequest request);
    Produto toEntity(ProdutoDTO produtoDTO);
    ProdutoResponse toProdutoResponse(Produto produto);
    List<ProdutoResponse> toResponseList(List<Produto> produtos);
}
