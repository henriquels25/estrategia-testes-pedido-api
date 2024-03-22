package io.henriquels25.testes.exemplo.pedidosapi.repository;

import io.henriquels25.testes.exemplo.pedidosapi.controller.ItemPedido;
import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T09:53:47-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public PedidoEntity PedidoToPedidoEntity(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoEntity.PedidoEntityBuilder pedidoEntity = PedidoEntity.builder();

        pedidoEntity.clienteId( pedido.clienteId() );
        pedidoEntity.enderecoEntrega( pedido.enderecoEntrega() );
        pedidoEntity.itens( itemPedidoListToItemPedidoEntityList( pedido.itens() ) );

        return pedidoEntity.build();
    }

    @Override
    public ItemPedidoEntity map(ItemPedido itemPedido) {
        if ( itemPedido == null ) {
            return null;
        }

        ItemPedidoEntity.ItemPedidoEntityBuilder itemPedidoEntity = ItemPedidoEntity.builder();

        itemPedidoEntity.produtoId( itemPedido.produtoId() );
        itemPedidoEntity.quantidade( itemPedido.quantidade() );

        return itemPedidoEntity.build();
    }

    protected List<ItemPedidoEntity> itemPedidoListToItemPedidoEntityList(List<ItemPedido> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemPedidoEntity> list1 = new ArrayList<ItemPedidoEntity>( list.size() );
        for ( ItemPedido itemPedido : list ) {
            list1.add( map( itemPedido ) );
        }

        return list1;
    }
}
