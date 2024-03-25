package io.henriquels25.testes.exemplo.pedidosapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static io.henriquels25.testes.exemplo.pedidosapi.utils.TestData.ENDERECO;
import static io.henriquels25.testes.exemplo.pedidosapi.utils.TestData.PEDIDO;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PedidoRepository.class, PedidoMapperImpl.class})
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Test
    void deveriaSalvarUmPedido() {
        Long id = pedidoRepository.save(PEDIDO);

        PedidoEntity pedidoEntity = pedidoJpaRepository.findById(id).orElseThrow();

        assertThat(pedidoEntity.getEnderecoEntrega()).isEqualTo(ENDERECO);
        assertThat(pedidoEntity.getItens()).hasSize(2);

        assertThat(pedidoEntity)
                .usingRecursiveComparison()
                .ignoringFields("id", "itens.id", "itens.pedido")
                .isEqualTo(pedidoMapper.pedidoToPedidoEntity(PEDIDO));
    }
}