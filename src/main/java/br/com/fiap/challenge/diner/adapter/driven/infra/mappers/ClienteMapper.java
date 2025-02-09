package br.com.fiap.challenge.diner.adapter.driven.infra.mappers;

import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarClienteRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarClienteRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.ClienteResponse;
import br.com.fiap.challenge.diner.core.domain.dto.ClienteDTO;
import br.com.fiap.challenge.diner.core.domain.entities.Cliente;
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
