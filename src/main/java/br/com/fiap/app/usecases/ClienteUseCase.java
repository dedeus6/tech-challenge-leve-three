package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.ClienteMapper;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.ClienteResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.app.repositories.ClienteRepository;
import br.com.fiap.utils.Paginacao;
import br.com.fiap.webui.dtos.ClienteDTO;
import br.com.fiap.app.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.errors.Errors.CLIENTE_EXISTE;
import static br.com.fiap.errors.Errors.CLIENTE_NAO_EXISTE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteResponse cadastrarCliente(ClienteDTO clienteDTO) {
        var cliente = getClienteByCpf(clienteDTO.getCpf());
        if (cliente != null)
            throw new BusinessException(CLIENTE_EXISTE, UNPROCESSABLE_ENTITY);

        var entity = clienteMapper.toEntity(clienteDTO);
        var entityResponse = clienteRepository.save(entity);
        return clienteMapper.toClienteResponse(entityResponse);
    }

    public ClienteResponse atualizaCliente(Long id, ClienteDTO clienteDTO) {
        var entity = getClienteById(id);
        entity.setNome(clienteDTO.getNome());
        entity.setTelefone(clienteDTO.getTelefone());
        entity.setEmail(clienteDTO.getEmail());
        Cliente entityResponse = clienteRepository.save(entity);
        return clienteMapper.toClienteResponse(entityResponse);
    }

    public void deletarCliente(Long id) {
        var categoria = getClienteById(id);
        clienteRepository.delete(categoria);
    }

    public ClienteResponse buscarClienteById(Long id) {
        return clienteMapper.toClienteResponse(getClienteById(id));
    }

    public ClienteResponse buscarCliente(String cpf) {
        var cliente = getClienteByCpf(cpf);
        if (cliente == null)
            throw new BusinessException(CLIENTE_NAO_EXISTE, UNPROCESSABLE_ENTITY);
        return clienteMapper.toClienteResponse(cliente);
    }

    public PaginacaoResponse<ClienteResponse> listarClientes(Integer page, Integer limit, String sort) {
        var pageable = Paginacao.getPageRequest(limit, page, sort, "id");
        var clientes = clienteRepository.findAll(pageable);
        List<ClienteResponse> clientesResponse = clienteMapper.toResponseList(clientes.getContent());
        return new PaginacaoResponse<ClienteResponse>().convertToResponse(new PageImpl(clientesResponse, clientes.getPageable(), 0L));
    }

    private Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CLIENTE_NAO_EXISTE, UNPROCESSABLE_ENTITY));
    }

    private Cliente getClienteByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
