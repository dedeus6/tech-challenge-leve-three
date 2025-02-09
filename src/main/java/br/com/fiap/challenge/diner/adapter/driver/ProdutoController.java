package br.com.fiap.challenge.diner.adapter.driver;

import br.com.fiap.challenge.diner.adapter.driven.infra.mappers.ProdutoMapper;
import br.com.fiap.challenge.diner.adapter.driver.request.AtualizarProdutoRequest;
import br.com.fiap.challenge.diner.adapter.driver.request.CadastrarProdutoRequest;
import br.com.fiap.challenge.diner.adapter.driver.response.ErrorResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.PaginacaoProdutoResponse;
import br.com.fiap.challenge.diner.adapter.driver.response.ProdutoResponse;
import br.com.fiap.challenge.diner.core.application.services.ProdutoService;
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
@Tag(name = "Produto")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/produto")
public class ProdutoController {

    private static final String ASC_DESC = "ASC/DESC";

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @Operation(summary = "Cadastrar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))})})
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody @Valid CadastrarProdutoRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(produtoService.cadastrarProduto(produtoMapper.toProdutoDto(request)));
    }

    @Operation(summary = "Atualizar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final AtualizarProdutoRequest request) {
        var produtoDTO = produtoMapper.toProdutoDto(request);
        return ResponseEntity.ok(produtoService.atualizaProduto(id, produtoDTO));
    }

    @Operation(summary = "Deletar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada com sucesso",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaProduto(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        produtoService.deletaProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProdutoById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(produtoService.buscarProdutoById(id));
    }

    @Operation(summary = "Listar produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoProdutoResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginacaoResponse<ProdutoResponse>> listarProdutos(
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
        return ResponseEntity.ok(produtoService.listarProdutos(page, limit, sort));
    }

    @Operation(summary = "Buscar produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginacaoProdutoResponse.class))})})
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<PaginacaoResponse<ProdutoResponse>> buscarProdutosPorCategoria(
            @Parameter(description = PAGE)
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1, message = PAGE_MINIMA)
            final Integer page,
            @Parameter(description = LIMIT)
            @RequestParam(required = false, defaultValue = "25")
            final Integer limit,
            @Parameter(description = SORT, example = ASC_DESC)
            @RequestParam(required = false, defaultValue = "DESC")
            final String sort,
            @Parameter(description = CATEGORIA_ID)
            @PathVariable(name = "categoriaId")
            final Long categoriaId) {
        return ResponseEntity.ok(produtoService.buscarProdutosPorCategoria(page, limit, sort, categoriaId));
    }

}
