package br.com.fiap.challenge.diner.adapter.driven.infra.mappers;

import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarCategoriaRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarCategoriaRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.CategoriaResponse;
import br.com.fiap.challenge.diner.core.domain.dto.CategoriaDTO;
import br.com.fiap.challenge.diner.core.domain.entities.Categoria;
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
