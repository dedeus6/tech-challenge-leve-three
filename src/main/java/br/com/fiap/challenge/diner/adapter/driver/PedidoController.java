package br.com.fiap.challenge.diner.adapter.driver;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.PedidoMapper;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarPedidoRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.SolicitarPagamentoRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.WebhookPagamentoRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.ErrorResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoEmpresaResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PedidoResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.SolicitarPagamentoResponse;
import br.com.fiap.challenge.diner.core.application.services.PedidoPagamentoService;
import br.com.fiap.challenge.diner.core.application.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.*;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.PAGE_MINIMA;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Pedido")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/pedido")
public class PedidoController {

    private static final String ASC_DESC = "ASC/DESC";

    private final PedidoService pedidoService;
    private final PedidoPagamentoService pedidoPagamentoService;
    private final PedidoMapper pedidoMapper;

    @Operation(summary = "Realizar Pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponse.class))})})
    @PostMapping
    public ResponseEntity<PedidoResponse> cadastrarPedido(
            @RequestBody @Valid CadastrarPedidoRequest request) {
        var requestDTO = pedidoMapper.toPedidoDTO(request);
        return ResponseEntity
                .status(CREATED)
                .body(pedidoService.cadastrarPedido(requestDTO));
    }

    @Operation(summary = "Buscar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedido(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        var response = pedidoService.buscarPedidoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza status do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponse.class))})})
    @PatchMapping("/status/{id}")
    public ResponseEntity<?> atualizaStatusPedido(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        var response = pedidoService.atualizaStatusPedido(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Listar pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoEmpresaResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<?> listarPedidos(
            @Parameter(description = PAGE)
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1, message = PAGE_MINIMA)
            final Integer page,
            @Parameter(description = LIMIT)
            @RequestParam(required = false, defaultValue = "25")
            final Integer limit) {
        var response = pedidoService.listarPedidos(page, limit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Solicitação de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitação de pagamento realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitarPagamentoResponse.class))})})
    @PostMapping("/pagamento/solicitar")
    public ResponseEntity<SolicitarPagamentoResponse> solicitarPagamento(
            @RequestBody @Valid SolicitarPagamentoRequest request) {
        var requestDTO = pedidoMapper.toPedidoPagamentoDTO(request);
        var response = pedidoPagamentoService.solicitarPagamento(requestDTO);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Operation(summary = "Webhook de notifição de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação de pagamento realizado com sucesso")})
    @PostMapping("/pagamento/webhook")
    public ResponseEntity<Void> webhookPagamento(
            @RequestBody @Valid WebhookPagamentoRequest request) {
        var requestDTO = pedidoMapper.toWebhookPagamentoDTO(request);
        pedidoPagamentoService.webhookPagamento(requestDTO);
        return ResponseEntity.status(OK).build();
    }
}
