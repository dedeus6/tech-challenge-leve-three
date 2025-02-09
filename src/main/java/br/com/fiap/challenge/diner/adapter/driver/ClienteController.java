package br.com.fiap.challenge.diner.adapter.driver;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.ClienteMapper;
import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarClienteRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarClienteRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.ClienteResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.ErrorResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoClienteResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoResponse;
import br.com.fiap.challenge.diner.core.application.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.CPF_CLIENTE;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ID;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.LIMIT;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.PAGE;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.SORT;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.PAGE_MINIMA;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.QUERY_PARAMS_REQUERIDO;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Cliente")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/cliente")
public class ClienteController {

    private static final String ASC_DESC = "ASC/DESC";

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Operation(summary = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))})})
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody @Valid CadastrarClienteRequest request) {
        var response = clienteService.cadastrarCliente(clienteMapper.toClienteDto(request));
        return ResponseEntity.status(CREATED).body(response);
    }

    @Operation(summary = "Atualizar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final AtualizarClienteRequest request) {
        var clienteDTO = clienteMapper.toClienteDto(request);
        return ResponseEntity.ok(clienteService.atualizaCliente(id, clienteDTO));
    }

    @Operation(summary = "Deletar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada com sucesso",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaProduto(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarClienteById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(clienteService.buscarClienteById(id));
    }

    @Operation(summary = "Buscar cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))})})
    @GetMapping
    public ResponseEntity<ClienteResponse> buscarCliente(
            @Parameter(description = CPF_CLIENTE)
            @RequestParam(required = false)
            @NotBlank(message = QUERY_PARAMS_REQUERIDO)
            final String cpf) {
        return ResponseEntity.ok(clienteService.buscarCliente(cpf));
    }

    @Operation(summary = "Listar Clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoClienteResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginacaoResponse<ClienteResponse>> listarClientes(
            @Parameter(description = PAGE)
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1, message = PAGE_MINIMA)
            final Integer page,
            @Parameter(description = LIMIT)
            @RequestParam(required = false, defaultValue = "25")
            final Integer limit,
            @Parameter(description = SORT, example = ASC_DESC)
            @RequestParam(required = false, defaultValue = "DESC")
            final String sort) {
        return ResponseEntity.ok(clienteService.listarClientes(page, limit, sort));
    }
}
