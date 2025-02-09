package br.com.fiap.challenge.diner.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @Column(name = "vlr_unitario")
    private Double vlrUnitario;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private String imagem;

    @Override
    public String toString() {
        return "Id=" + id;
    }
}
