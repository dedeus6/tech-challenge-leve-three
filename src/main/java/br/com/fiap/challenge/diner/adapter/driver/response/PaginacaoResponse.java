package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaginacaoResponse<T> {

    @Schema(description = HAS_NEXT)
    private Boolean hasNext;
    @Schema(description = HAS_PREVIOUS)
    private Boolean hasPrevious;
    @Schema(description = PAGE_NUMBER)
    private Integer pageNumber;
    @Schema(description = PAGE_SIZE)
    private Integer pageSize;
    @Schema(description = ITEMS)
    private List<T> items;

    public PaginacaoResponse convertToResponse(Page<T> lista) {
        this.hasNext = lista.getContent().size() >= lista.getPageable().getPageSize();
        this.hasPrevious = lista.getNumber() > 0;
        this.pageNumber = lista.getNumber() + 1;
        this.pageSize = lista.getPageable().getPageSize();
        this.items = lista.getContent();
        return this;
    }
}
