package io.henriquels25.testes.exemplo.pedidosapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "estoque", url = "${estoque.url}")
public interface EstoqueClient {

    @GetMapping("/produtos/{id}")
    EstoqueResponse get(@PathVariable("id") String produtoId);


}
