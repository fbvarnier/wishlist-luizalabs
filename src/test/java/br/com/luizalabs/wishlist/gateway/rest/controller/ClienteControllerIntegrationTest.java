package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.AbstractContainerBaseTest;
import br.com.luizalabs.wishlist.Integration;
import br.com.luizalabs.wishlist.core.response.ConsultarClienteResponseList;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import net.jcip.annotations.NotThreadSafe;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.URISyntaxException;

@Testcontainers
@NotThreadSafe
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
@Category(Integration.class)
class ClienteControllerIntegrationTest extends AbstractContainerBaseTest {

    private String urlCliente = this.URL_BASE+"/cliente";
    @Autowired
    private ClienteController clienteController;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Dado nome do cliente não é vazio ou nulo, deve buscar na base e retornar um unico cliente.")
    void buscaClientePorNomeERetornaUmResultadoComRest() throws URISyntaxException {
        inicializaCliente(mongoTemplate);

        URI uri = new URI(urlCliente+"/por-nome-like/Cliente%201");
        ResponseEntity<String> entity = new TestRestTemplate()
                .getForEntity(uri, String.class);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getBody());
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        dropCliente(mongoTemplate);
    }

    @Test
    @DisplayName("Dado nome do cliente não é vazio ou nulo, deve buscar na base e retornar um unico cliente.")
    void buscaClientePorNomeERetornaUmResultado(){
        inicializaCliente(mongoTemplate);

        ConsultarClienteResponseList consultarClienteResponseList = clienteController.consultaClientePorNome("Cliente 1");

        Assertions.assertNotNull(consultarClienteResponseList);
        Assertions.assertEquals("SUCESSO", consultarClienteResponseList.getStatus());
        Assertions.assertEquals(1, consultarClienteResponseList.getData().size());
        dropCliente(mongoTemplate);
    }

    @Test
    @DisplayName("Dado nome do cliente não é vazio ou nulo, deve buscar na base e retornar quatro clientes.")
    void buscaClientePorNomeERetornaQuatroResultados(){
        inicializaCliente(mongoTemplate);

        ConsultarClienteResponseList consultarClienteResponseList = clienteController.consultaClientePorNome("Cliente");

        Assertions.assertNotNull(consultarClienteResponseList);
        Assertions.assertEquals("SUCESSO", consultarClienteResponseList.getStatus());
        Assertions.assertEquals(4, consultarClienteResponseList.getData().size());
        dropCliente(mongoTemplate);
    }
    @Test
    @DisplayName("Dado nome do cliente é vazio, deve buscar na base e retornar quatro clientes.")
    void buscaClienteComNomeVazioERetornaQuatroResultados(){
        inicializaCliente(mongoTemplate);

        ConsultarClienteResponseList consultarClienteResponseList = clienteController.consultaClientePorNome("");

        Assertions.assertNotNull(consultarClienteResponseList);
        Assertions.assertEquals("SUCESSO", consultarClienteResponseList.getStatus());
        Assertions.assertEquals(4, consultarClienteResponseList.getData().size());
        dropCliente(mongoTemplate);
    }
    @Test
    @DisplayName("Dado nome do cliente é nulo, deve lancar exception.")
    void buscaClienteComNomeNuloERetornaQuatroResultados(){
        inicializaCliente(mongoTemplate);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> clienteController.consultaClientePorNome(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("Nome não pode ser nulo.");

        dropCliente(mongoTemplate);
    }

}
