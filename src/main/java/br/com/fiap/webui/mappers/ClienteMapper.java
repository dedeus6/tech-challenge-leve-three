package br.com.fiap.webui.mappers;

import br.com.fiap.webui.dtos.ClienteDTO;
import br.com.fiap.app.entities.Cliente;
import br.com.fiap.webui.dtos.request.AtualizarClienteRequest;
import br.com.fiap.webui.dtos.request.CadastrarClienteRequest;
import br.com.fiap.webui.dtos.response.ClienteResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toClienteDto(CadastrarClienteRequest request);

    ClienteDTO toClienteDto(AtualizarClienteRequest request);

    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteResponse toClienteResponse(Cliente cliente);

    List<ClienteResponse> toResponseList(List<Cliente> listaCliente);
}
