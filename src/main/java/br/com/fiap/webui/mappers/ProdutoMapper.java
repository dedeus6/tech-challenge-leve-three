package br.com.fiap.webui.mappers;

import br.com.fiap.webui.dtos.request.AtualizarProdutoRequest;
import br.com.fiap.webui.dtos.request.CadastrarProdutoRequest;
import br.com.fiap.webui.dtos.response.ProdutoResponse;
import br.com.fiap.webui.dtos.ProdutoDTO;
import br.com.fiap.app.entities.Produto;
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
