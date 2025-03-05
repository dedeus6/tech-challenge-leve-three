package br.com.fiap.webui.mappers;

import br.com.fiap.webui.dtos.request.AtualizarCategoriaRequest;
import br.com.fiap.webui.dtos.request.CadastrarCategoriaRequest;
import br.com.fiap.webui.dtos.response.CategoriaResponse;
import br.com.fiap.webui.dtos.CategoriaDTO;
import br.com.fiap.app.entities.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO toCategoriaDto(CadastrarCategoriaRequest request);

    CategoriaDTO toCategoriaDto(AtualizarCategoriaRequest request);

    Categoria toEntity(CategoriaDTO categoriaDTO);

    CategoriaResponse toCategoriaResponse(Categoria categoria);

    List<CategoriaResponse> toResponseList(List<Categoria> categorias);
}
