package io.henriquels25.testes.exemplo.pedidosapi.service;

import io.henriquels25.testes.exemplo.pedidosapi.client.EstoqueClient;
import io.henriquels25.testes.exemplo.pedidosapi.client.EstoqueResponse;
import io.henriquels25.testes.exemplo.pedidosapi.controller.ItemPedido;
import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;
import io.henriquels25.testes.exemplo.pedidosapi.kafka.PedidoCriadoProducer;
import io.henriquels25.testes.exemplo.pedidosapi.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.henriquels25.testes.exemplo.pedidosapi.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private EstoqueClient estoqueClient;

    @Mock
    private PedidoCriadoProducer pedidoCriadoProducer;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveriaCriarUmPedidoQuandoTodosItensPossuemEstoque() {
        Long pedidoId = 1L;

        when(pedidoRepository.save(PEDIDO)).thenReturn(pedidoId);
        when(estoqueClient.get(PRODUTO_ID_1)).thenReturn(new EstoqueResponse(PRODUTO_ID_1, 10L));
        when(estoqueClient.get(PRODUTO_ID_2)).thenReturn(new EstoqueResponse(PRODUTO_ID_2, 10L));

        pedidoService.cria(PEDIDO);

        verify(pedidoCriadoProducer).sendPedidoCriadoMessage(pedidoId);
        verify(pedidoRepository).save(PEDIDO);
    }

    @Test
    void naoDeveriaCriarUmPedidoQuandoNaoHaEstoque() {
        ItemPedido itemPedido1 = new ItemPedido(PRODUTO_ID_1, 2);
        ItemPedido itemPedido2 = new ItemPedido(PRODUTO_ID_2, 1);

        Pedido pedido = new Pedido(CLIENTE_ID, List.of(itemPedido1, itemPedido2), ENDERECO);
        when(estoqueClient.get(PRODUTO_ID_1)).thenReturn(new EstoqueResponse(PRODUTO_ID_1, 0L));

        assertThrows(EstoqueExcedidoException.class, () ->pedidoService.cria(pedido));

        verifyNoInteractions(pedidoCriadoProducer);
        verifyNoInteractions(pedidoRepository);
    }
}