package io.henriquels25.testes.exemplo.pedidosapi.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(topics = {"pedidos-criados-v1"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class PedidoCriadoProducerTest {
    private static final String TOPIC_NAME = "pedidos-criados-v1";

    @Autowired
    private PedidoCriadoProducer pedidoCriadoProducer;

    @Autowired
    private EmbeddedKafkaBroker broker;

    private KafkaTestUtils kafkaTestUtils;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void deveriaEnviarUmEventoDePedidoCriado() throws JSONException {
        Consumer<String, String> consumer =
                kafkaTestUtils.createConsumer(TOPIC_NAME);

        Long pedidoId = 1L;
        pedidoCriadoProducer.sendPedidoCriadoMessage(pedidoId);

        ConsumerRecord<String, String> record = kafkaTestUtils.getLastRecord(consumer, TOPIC_NAME);

        var jsonEvent = new JSONObject(record.value());
        assertThat(jsonEvent.getLong("pedidoId")).isEqualTo(pedidoId);
    }
}