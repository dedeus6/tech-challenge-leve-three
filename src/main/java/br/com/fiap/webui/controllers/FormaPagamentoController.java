package br.com.fiap.webui.controllers;

import br.com.fiap.webui.mappers.FormaPagamentoMapper;
import br.com.fiap.webui.dtos.response.ErrorResponse;
import br.com.fiap.webui.dtos.response.FormaPagamentoResponse;
import br.com.fiap.webui.dtos.response.PaginacaoFormaPagamentoResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.app.usecases.FormaPagamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fiap.webui.description.Descriptions.LIMIT;
import static br.com.fiap.webui.description.Descriptions.PAGE;
import static br.com.fiap.webui.description.Descriptions.SORT;
import static br.com.fiap.errors.Errors.PAGE_MINIMA;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Forma de Pagamento")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/forma-pagamento")
public class FormaPagamentoController {

    private static final String ASC_DESC = "ASC/DESC";

    private final FormaPagamentoUseCase service;
    private final FormaPagamentoMapper mapper;

    @Operation(summary = "Listar forma de pagamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoFormaPagamentoResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginacaoResponse<FormaPagamentoResponse>> listarFormaPagamentos(
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
        return ResponseEntity.ok(service.listarFormaPagamentos(page, limit, sort));
    }
}
