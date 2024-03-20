package io.henriquels25.testes.exemplo.pedidosapi.repository;

import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoRepository {

    private final PedidoJpaRepository pedidoJpaRepository;
    private final PedidoMapper pedidoMapper;

    public Long save(Pedido pedido) {
        PedidoEntity pedidoEntity = pedidoMapper.PedidoToPedidoEntity(pedido);
        for (ItemPedidoEntity itemPedidoEntity : pedidoEntity.getItens()){
            itemPedidoEntity.setPedido(pedidoEntity);
        }
        PedidoEntity saved = pedidoJpaRepository.save(pedidoEntity);
        return saved.getId();
    }

}
