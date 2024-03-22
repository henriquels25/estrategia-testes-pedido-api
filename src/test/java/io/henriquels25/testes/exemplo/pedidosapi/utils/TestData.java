package io.henriquels25.testes.exemplo.pedidosapi.utils;

import io.henriquels25.testes.exemplo.pedidosapi.controller.ItemPedido;
import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;

import java.util.List;

public class TestData {
    public static final String PRODUTO_ID_1 = "1";
    public static final String PRODUTO_ID_2 = "2";

    public static final String CLIENTE_ID = "CLIENTE_1";
    public static final String ENDERECO = "XXXXXXXXXX";

    public static final ItemPedido ITEM_PEDIDO_1 = new ItemPedido(PRODUTO_ID_1, 2);
    public static final ItemPedido ITEM_PEDIDO_2 = new ItemPedido(PRODUTO_ID_2, 1);
    public static final Pedido PEDIDO = new Pedido(CLIENTE_ID, List.of(ITEM_PEDIDO_1, ITEM_PEDIDO_2), ENDERECO);

}
