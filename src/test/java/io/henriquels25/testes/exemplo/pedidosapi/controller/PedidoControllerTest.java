package io.henriquels25.testes.exemplo.pedidosapi.controller;

import io.henriquels25.testes.exemplo.pedidosapi.service.PedidoService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.util.TestContextResourceUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static io.henriquels25.testes.exemplo.pedidosapi.utils.TestData.PEDIDO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @MockBean
    private PedidoService pedidoService;
    @Autowired
    private MockMvc mvc;

    @Test
    void criaPedido() throws Exception {
        String pedidoJson = new ClassPathResource("json/pedido.json")
                .getContentAsString(Charset.defaultCharset());

        mvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoJson))
                .andExpect(status().isCreated());

        verify(pedidoService).cria(PEDIDO);
    }
}