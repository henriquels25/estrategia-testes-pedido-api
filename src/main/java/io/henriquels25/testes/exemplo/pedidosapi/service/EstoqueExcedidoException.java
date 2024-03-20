package io.henriquels25.testes.exemplo.pedidosapi.service;

public class EstoqueExcedidoException extends RuntimeException {
    public EstoqueExcedidoException(String s) {
        super(s);
    }
}
