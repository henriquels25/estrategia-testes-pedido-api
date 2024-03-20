package io.henriquels25.testes.exemplo.pedidosapi.controller;

import java.util.List;

public record Pedido(String clienteId, List<ItemPedido> itens, String enderecoEntrega) {
}
