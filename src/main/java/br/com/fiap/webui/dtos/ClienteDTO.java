package br.com.fiap.webui.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
}
