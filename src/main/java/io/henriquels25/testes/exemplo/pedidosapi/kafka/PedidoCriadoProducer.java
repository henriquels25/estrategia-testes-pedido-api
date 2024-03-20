package io.henriquels25.testes.exemplo.pedidosapi.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoCriadoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendPedidoCriadoMessage(Long pedidoId){
        log.info("Enviando mensagem de pedido criado para id {} no tópico pedido-criado-v1", pedidoId);
        String json = objectMapper.writeValueAsString(new PedidoCriadoMessage(pedidoId));
        kafkaTemplate.send("pedidos-criados-v1", json);
    }

}
