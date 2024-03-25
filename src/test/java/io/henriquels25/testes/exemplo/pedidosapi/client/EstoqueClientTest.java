package io.henriquels25.testes.exemplo.pedidosapi.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class EstoqueClientTest {

    @Autowired
    private EstoqueClient estoqueClient;

    @Test
    void deveriaBuscarAQuantidadeDaApiDeEstoque() {
        stubFor(get(urlEqualTo("/produtos/1"))
                .willReturn(aResponse().withBodyFile("estoque_produto_1_response.json")
                        .withHeader("Content-Type", "application/json")));

        EstoqueResponse response = estoqueClient.get("1");

        assertThat(response).isEqualTo(new EstoqueResponse("1", 20L));
    }
}