package io.henriquels25.testes.exemplo.pedidosapi.service;

import io.henriquels25.testes.exemplo.pedidosapi.client.EstoqueClient;
import io.henriquels25.testes.exemplo.pedidosapi.controller.Pedido;
import io.henriquels25.testes.exemplo.pedidosapi.kafka.PedidoCriadoProducer;
import io.henriquels25.testes.exemplo.pedidosapi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final EstoqueClient estoqueClient;
    private final PedidoCriadoProducer pedidoCriadoProducer;
    private final PedidoRepository pedidoRepository;

    @SneakyThrows
    public Long cria(Pedido pedido){
        log.info("Iniciando criação de pedido");

        for (var item : pedido.itens()) {
           log.trace("Fazendo requisição de estoque para produto {}", item.produtoId());
           Long estoqueAtual = estoqueClient.get(item.produtoId()).quantidade();
           if (item.quantidade() > estoqueAtual){
               throw new EstoqueExcedidoException("O produto " + item.produtoId() + " não está mais no estoque");
           }
        }
        Long pedidoId = pedidoRepository.save(pedido);
        pedidoCriadoProducer.sendPedidoCriadoMessage(pedidoId);
        return pedidoId;
    }

}
