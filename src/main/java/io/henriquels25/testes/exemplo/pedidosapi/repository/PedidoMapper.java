package io.henriquels25.testes.exemplo.pedidosapi.repository;

import io.henriquels25.testes.exemplo.pedidosapi.controller.ItemPedido;
import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoEntity pedidoToPedidoEntity(Pedido pedido);
    ItemPedidoEntity map(ItemPedido itemPedido);
}
