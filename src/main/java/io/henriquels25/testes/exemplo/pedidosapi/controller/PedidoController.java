package io.henriquels25.testes.exemplo.pedidosapi.controller;

import io.henriquels25.testes.exemplo.pedidosapi.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;
    @PostMapping
    public ResponseEntity criaPedido(@RequestBody Pedido pedido) {
        log.info("criando pedido para o cliente {}", pedido.clienteId());
        Long pedidoId = pedidoService.cria(pedido);
        return ResponseEntity.created(URI.create(String.format("/pedidos/%s", pedidoId))).build();
    }
}
