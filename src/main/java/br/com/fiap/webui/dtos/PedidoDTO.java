package br.com.fiap.webui.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @Builder.Default
    private LocalDateTime data = LocalDateTime.now();

    private Long clienteId;

    private Long empresaId;

    @Builder.Default
    private String status = "R";

    @Builder.Default
    private List<ItemDTO> itens = new ArrayList<>();

    private String observacao;

}
