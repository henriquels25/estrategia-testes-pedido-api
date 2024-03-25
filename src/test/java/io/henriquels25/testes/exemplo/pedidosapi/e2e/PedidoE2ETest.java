package io.henriquels25.testes.exemplo.pedidosapi.e2e;

import io.henriquels25.testes.exemplo.pedidosapi.kafka.KafkaTestUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
@EmbeddedKafka(topics = {"pedidos-criados-v1"},
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class PedidoE2ETest {
    private static final String TOPIC_NAME = "pedidos-criados-v1";
    @Autowired
    private MockMvc mvc;
    private KafkaTestUtils kafkaTestUtils;
    @Autowired
    private EmbeddedKafkaBroker broker;

    @BeforeEach
    void prepare() {
        this.kafkaTestUtils = new KafkaTestUtils(broker);
    }

    @Test
    void deveriaCriarUmPedidoEEnviarOEvento() throws Exception {
        String pedidoJson = new ClassPathResource("json/pedido.json")
                .getContentAsString(Charset.defaultCharset());

        Consumer<String, String> consumer =
                kafkaTestUtils.createConsumer(TOPIC_NAME);

        stubFor(get(urlEqualTo("/produtos/1"))
                .willReturn(aResponse().withBodyFile("estoque_produto_1_response.json")
                        .withHeader("Content-Type", "application/json")));

        stubFor(get(urlEqualTo("/produtos/2"))
                .willReturn(aResponse().withBodyFile("estoque_produto_2_response.json")
                        .withHeader("Content-Type", "application/json")));

        mvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoJson))
                .andExpect(status().isCreated());

        ConsumerRecord<String, String> record = kafkaTestUtils.getLastRecord(consumer, TOPIC_NAME);

        var jsonEvent = new JSONObject(record.value());
        assertThat(jsonEvent.getLong("pedidoId")).isNotNull();
    }



}
