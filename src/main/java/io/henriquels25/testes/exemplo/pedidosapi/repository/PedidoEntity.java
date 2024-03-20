package io.henriquels25.testes.exemplo.pedidosapi.repository;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteId;
    private String enderecoEntrega;

    @OneToMany(mappedBy="pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoEntity> itens;
}
