package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.AbstractContainerBaseTest;
import br.com.luizalabs.wishlist.Integration;
import br.com.luizalabs.wishlist.core.request.AdicionarNovaWishlistRequest;
import br.com.luizalabs.wishlist.core.request.AdicionarProdutoWishlistRequest;
import br.com.luizalabs.wishlist.core.response.*;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.OperacaoInvalidaException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import net.jcip.annotations.NotThreadSafe;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@NotThreadSafe
@SpringBootTest
@Category(Integration.class)
class WishlistControllerIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private WishlistController wishlistController;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Dado que o produto não existe, deve lancar exception.")
    void deveLancarExceptionQuandoProdutoNaoExiste() {
        inicializaWishlist(mongoTemplate);
        inicializaProduto(mongoTemplate);

        AdicionarProdutoWishlistRequest request = new AdicionarProdutoWishlistRequest("1", "1", "23");

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> wishlistController.adicionarProdutoNaWishlist(request))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Produto não existe.");

        dropWishlist(mongoTemplate);
        dropProduto(mongoTemplate);
    }
    @Test
    @DisplayName("Dado que a wishlist não existe, deve lancar exception.")
    void deveLancarExceptionQuandoWishlistNaoExiste() {
        inicializaWishlist(mongoTemplate);
        inicializaProduto(mongoTemplate);

        AdicionarProdutoWishlistRequest request = new AdicionarProdutoWishlistRequest("3", "1", "1");

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> wishlistController.adicionarProdutoNaWishlist(request))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Wishlist não existe.");

        dropWishlist(mongoTemplate);
        dropProduto(mongoTemplate);
    }
    @Test
    @DisplayName("Dado que a wishlist existe e está cheia, deve lancar exception.")
    void deveLancarExceptionQuandoWishlistEstiverCheia() {
        inicializaWishlist(mongoTemplate);
        inicializaProduto(mongoTemplate);

        AdicionarProdutoWishlistRequest request = new AdicionarProdutoWishlistRequest("1", "1", "21");

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> wishlistController.adicionarProdutoNaWishlist(request))
                .isInstanceOf(OperacaoInvalidaException.class)
                .hasMessage("Wishlist já está cheia.");

        dropWishlist(mongoTemplate);
        dropProduto(mongoTemplate);
    }
    @Test
    @DisplayName("Dado que a wishlist existe e não está cheia, deve adicionar um produto.")
    void deveAdicionarProdutoNaWishlis() {
        inicializaWishlist(mongoTemplate);
        inicializaProduto(mongoTemplate);

        AdicionarProdutoWishlistRequest request = new AdicionarProdutoWishlistRequest("2", "1", "1");

        AdicionarProdutoWishlistResponse response = wishlistController.adicionarProdutoNaWishlist(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(1, response.getData().getIdProdutos().size());

        dropWishlist(mongoTemplate);
        dropProduto(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a wishlists não foi informada, deve lancar exception.")
    void deveLancarExceptionQuandoConsultarPordutosEWishlistNaoInformada() {
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> wishlistController.consultaProdutosPorWishlist(""))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
    @Test
    @DisplayName("Dado que a wishlist existe mas não tem produtos, deve lancar exception.")
    void deveLancarExceptionQuandoConsultarPordutosEWishlistEstiverVazia() {
        inicializaWishlist(mongoTemplate);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> wishlistController.consultaProdutosPorWishlist("2"))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("A wishlist não possui produtos.");

        dropWishlist(mongoTemplate);
    }
    @Test
    @DisplayName("Dado que a wishlist existe mas tem produtos, retornar a lista de produtos.")
    void deveConsultarProdutosDaWishlist() {
        inicializaWishlist(mongoTemplate);
        inicializaProduto(mongoTemplate);

        ConsultarProdutosPorWishlistResponse response = wishlistController.consultaProdutosPorWishlist("1");

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(20, response.getData().size());

        dropWishlist(mongoTemplate);
        dropProduto(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a wishlist existe mas tem produtos, retornar a lista de produtos.")
    void deveAdicionarNovaWishlist() {
        inicializaWishlist(mongoTemplate);
        inicializaCliente(mongoTemplate);

        AdicionarNovaWishlistRequest request = new AdicionarNovaWishlistRequest("Nova Lista", "2");

        AdicionarNovaWishlistResponse response = wishlistController.adicionarNovaWishlist(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("2", response.getData().getIdCliente());
        Assertions.assertEquals("Nova Lista", response.getData().getNome());

        dropWishlist(mongoTemplate);
        dropCliente(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a wishlist existe e o produto faz parte dela, retornar verdadeiro.")
    void deveVerificarSeProdutosExisteNaWishlist() {
        inicializaWishlist(mongoTemplate);

        VerificarProdutoExisteNaWishlistResponse response = wishlistController.verificaProdutosExisteNaWishlist("1", "1");

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isData());

        dropWishlist(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a existem wishtlists, retornar as wishlists com nome compatível.")
    void deveConsultarWishlistPorNome() {
        inicializaWishlist(mongoTemplate);

        ConsultarWishlistResponseList response = wishlistController.consultaWishlistPorNome("Lista");

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(2, response.getData().size());
        dropWishlist(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a wishtlist existe, retornar a wishlist.")
    void deveConsultarWishlistPorId() {
        inicializaWishlist(mongoTemplate);

        ConsultarWishlistResponse response = wishlistController.consultaWishlistPorId("2");

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Lista Vazia", response.getData().getNome());
        dropWishlist(mongoTemplate);
    }

    @Test
    @DisplayName("Dado que a wishtlist existe e o produto faz parte dela, deve remover o produto da wishlist.")
    void deveRemoverProdutoDaWishList() {
        inicializaWishlist(mongoTemplate);

        wishlistController.removerProdutoDaWishlist("1", "1");

        ConsultarWishlistResponse response = wishlistController.consultaWishlistPorId("1");

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(19, response.getData().getIdProdutos().size());
        dropWishlist(mongoTemplate);
    }
}
