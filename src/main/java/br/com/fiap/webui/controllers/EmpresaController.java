package br.com.fiap.webui.controllers;

import br.com.fiap.webui.mappers.EmpresaMapper;
import br.com.fiap.webui.dtos.request.AtualizarEmpresaRequest;
import br.com.fiap.webui.dtos.request.CadastrarEmpresaRequest;
import br.com.fiap.webui.dtos.response.EmpresaResponse;
import br.com.fiap.webui.dtos.response.ErrorResponse;
import br.com.fiap.webui.dtos.response.PaginacaoEmpresaResponse;
import br.com.fiap.app.usecases.EmpresaServiceUseCase;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fiap.webui.description.Descriptions.ID;
import static br.com.fiap.webui.description.Descriptions.LIMIT;
import static br.com.fiap.webui.description.Descriptions.PAGE;
import static br.com.fiap.webui.description.Descriptions.SORT;
import static br.com.fiap.errors.Errors.PAGE_MINIMA;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Empresa")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/empresa")
public class EmpresaController {

    private static final String ASC_DESC = "ASC/DESC";

    private final EmpresaServiceUseCase service;
    private final EmpresaMapper mapper;

    @Operation(summary = "Cadastrar empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaResponse.class))})})
    @PostMapping
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody @Valid CadastrarEmpresaRequest request) {
        var empresaDTO = mapper.toEmpresaDTO(request);
        var response = service.cadastrarEmpresa(empresaDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Buscar empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEmpresaById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        var response = service.buscarEmpresaById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEmpresaById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final AtualizarEmpresaRequest request) {
        var empresaDTO = mapper.toEmpresaDTO(request);
        var response = service.atualizarEmpresaById(id, empresaDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Listar empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoEmpresaResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<?> listarEmpresas(
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
        var response = service.listarEmpresas(page, limit, sort);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
