package br.com.fiap.challenge.diner.adapter.driver;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.CategoriaMapper;
import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarCategoriaRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarCategoriaRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.*;
import br.com.fiap.challenge.diner.core.application.services.CategoriaService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.*;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.PAGE_MINIMA;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Categoria")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/categoria")
public class CategoriaController {

    private static final String ASC_DESC = "ASC/DESC";

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @Operation(summary = "Cadastrar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponse.class))})})
    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrarCategoria(@RequestBody @Valid CadastrarCategoriaRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(categoriaService.cadastrarCategoria(categoriaMapper.toCategoriaDto(request)));
    }

    @Operation(summary = "Atualizar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final AtualizarCategoriaRequest request) {
        var categoriaDto = categoriaMapper.toCategoriaDto(request);
        return ResponseEntity.ok(categoriaService.atualizaCategoria(id, categoriaDto));
    }

    @Operation(summary = "Deletar categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada com sucesso",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCategoria(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar categoria por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarCategoriaById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaById(id));
    }

    @Operation(summary = "Listar categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoCategoriaResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginacaoResponse<ProdutoResponse>> listarCategorias(
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
        return ResponseEntity.ok(categoriaService.listarCategorias(page, limit, sort));
    }

}
