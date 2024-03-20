package io.henriquels25.testes.exemplo.pedidosapi.repository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produtoId;
    private int quantidade;

    @ManyToOne
    @JoinColumn(name="pedido_id", nullable=false)
    private PedidoEntity pedido;
}
