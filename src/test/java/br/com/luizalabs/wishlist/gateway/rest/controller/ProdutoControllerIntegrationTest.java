package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.AbstractContainerBaseTest;
import br.com.luizalabs.wishlist.Integration;
import br.com.luizalabs.wishlist.core.response.ConsultarProdutoResponseList;
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
class ProdutoControllerIntegrationTest extends AbstractContainerBaseTest {

    private String urlProduto = this.URL_BASE+"/produto";
    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Dado nome do produto não é vazio ou nulo, deve buscar na base e retornar um unico produto.")
    void buscaProdutoPorNomeERetornaUmResultadoComRest() throws URISyntaxException {
        inicializaProduto(mongoTemplate);

        URI uri = new URI(urlProduto+"/por-nome-like/Produto%2022");
        ResponseEntity<String> entity = new TestRestTemplate()
                .getForEntity(uri, String.class);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getBody());
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        dropProduto(mongoTemplate);
    }

    @Test
    @DisplayName("Dado nome do produto não é vazio ou nulo, deve buscar na base e retornar um unico produto.")
    void buscaProdutoPorNomeERetornaUmResultado(){
        inicializaProduto(mongoTemplate);

        ConsultarProdutoResponseList consultarProdutoResponseList = produtoController.consultaProdutoPorNome("Produto 22");

        Assertions.assertNotNull(consultarProdutoResponseList);
        Assertions.assertEquals("SUCESSO", consultarProdutoResponseList.getStatus());
        Assertions.assertEquals(1, consultarProdutoResponseList.getData().size());
        dropProduto(mongoTemplate);
    }

    @Test
    @DisplayName("Dado nome do produto não é vazio ou nulo, deve buscar na base e retornar quatro produtos.")
    void buscaProdutoPorNomeERetornaQuatroResultados(){
        inicializaProduto(mongoTemplate);

        ConsultarProdutoResponseList consultarProdutoResponseList = produtoController.consultaProdutoPorNome("Produto 2");

        Assertions.assertNotNull(consultarProdutoResponseList);
        Assertions.assertEquals("SUCESSO", consultarProdutoResponseList.getStatus());
        Assertions.assertEquals(4, consultarProdutoResponseList.getData().size());
        dropProduto(mongoTemplate);
    }
    @Test
    @DisplayName("Dado nome do produto é vazio, deve buscar na base e retornar 22 produtos.")
    void buscaProdutoComNomeVazioERetorna22Resultados(){
        inicializaProduto(mongoTemplate);

        ConsultarProdutoResponseList consultarProdutoResponseList = produtoController.consultaProdutoPorNome("");

        Assertions.assertNotNull(consultarProdutoResponseList);
        Assertions.assertEquals("SUCESSO", consultarProdutoResponseList.getStatus());
        Assertions.assertEquals(22, consultarProdutoResponseList.getData().size());
        dropProduto(mongoTemplate);
    }
    @Test
    @DisplayName("Dado nome do produto é nulo, deve lancar exception.")
    void buscaProdutoComNomeNuloERetornaQuatroResultados(){
        inicializaProduto(mongoTemplate);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> produtoController.consultaProdutoPorNome(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("Nome não pode ser nulo.");

        dropProduto(mongoTemplate);
    }

}
