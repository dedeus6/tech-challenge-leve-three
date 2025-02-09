package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.FIELD;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.FIELD_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ErrorField {

    @Schema(description = FIELD)
    private String field;
    @Schema(description = FIELD_MESSAGE)
    private String message;
}
