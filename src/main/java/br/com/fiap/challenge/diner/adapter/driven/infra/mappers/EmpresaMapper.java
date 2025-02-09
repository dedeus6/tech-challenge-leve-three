package br.com.fiap.challenge.diner.adapter.driven.infra.mappers;

import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarEmpresaRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarEmpresaRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.EmpresaResponse;
import br.com.fiap.challenge.diner.core.domain.dto.EmpresaDTO;
import br.com.fiap.challenge.diner.core.domain.entities.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaDTO toEmpresaDTO(CadastrarEmpresaRequest request);
    EmpresaDTO toEmpresaDTO(AtualizarEmpresaRequest request);
    Empresa toEntity(EmpresaDTO empresaDTO);
    EmpresaResponse toEmpresaResponse(Empresa empresa);
    List<EmpresaResponse> toResponseList(List<Empresa> empresas);
}
