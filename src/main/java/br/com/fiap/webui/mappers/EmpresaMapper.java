package br.com.fiap.webui.mappers;

import br.com.fiap.webui.dtos.request.AtualizarEmpresaRequest;
import br.com.fiap.webui.dtos.request.CadastrarEmpresaRequest;
import br.com.fiap.webui.dtos.response.EmpresaResponse;
import br.com.fiap.webui.dtos.EmpresaDTO;
import br.com.fiap.app.entities.Empresa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaDTO toEmpresaDTO(CadastrarEmpresaRequest request);

    EmpresaDTO toEmpresaDTO(AtualizarEmpresaRequest request);

    Empresa toEntity(EmpresaDTO empresaDTO);

    EmpresaResponse toEmpresaResponse(Empresa empresa);

    List<EmpresaResponse> toResponseList(List<Empresa> empresas);
}
