package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.response.VerificarProdutoExisteNaWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarProdutoExisteNaWishlistInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VerificarProdutoExisteNaWishlistTest {

    VerificarProdutoExisteNaWishlistInput verificarProdutoExisteNaWishlistInput;

    @Mock
    private ConsultarWishlistPorId consultarWishlistPorId;

    @BeforeEach
    void init(){
        verificarProdutoExisteNaWishlistInput = new VerificarProdutoExisteNaWishlist(consultarWishlistPorId);
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista com o produto, " +
            "deve retornar true.")
    void deveRetornarTrueSeExisteOProdutoNaWishlist(){
        var consultarWishlistResponse = new ConsultarWishlistResponse("1", "nome", "1", List.of("2"));
        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(consultarWishlistResponse);

        VerificarProdutoExisteNaWishlistResponse wishlistResponse = verificarProdutoExisteNaWishlistInput
                .verificaProdutoExisteNaWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertTrue(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista mas sem o produto passado, " +
            "deve retornar false.")
    void deveRetornarFalseSeNaoExisteOProdutoNaWishlist(){
        var consultarWishlistResponse = new ConsultarWishlistResponse("1", "nome", "1", List.of("3"));
        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(consultarWishlistResponse);

        VerificarProdutoExisteNaWishlistResponse wishlistResponse = verificarProdutoExisteNaWishlistInput
                .verificaProdutoExisteNaWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertFalse(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto não sejam nulos ou vazio, e que a wishlist exista mas sem nenhum produto, " +
            "deve retornar false.")
    void deveRetornarFalseSeWishlistNaoTemProdutos(){
        var consultarWishlistResponse = new ConsultarWishlistResponse("1", "nome", "1", List.of());
        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(consultarWishlistResponse);

        VerificarProdutoExisteNaWishlistResponse wishlistResponse = verificarProdutoExisteNaWishlistInput
                .verificaProdutoExisteNaWishlist("1", "2");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertFalse(wishlistResponse.isData());
    }
    @Test
    @DisplayName("Dado que a wishlist passada é nula e o produto não é nulo ou vazio, deve lançar exception.")
    void deveLancarExceptionSeWishlistNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> verificarProdutoExisteNaWishlistInput.verificaProdutoExisteNaWishlist(null, "2"))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
    @Test
    @DisplayName("Dado que a wishlist não é nula ou vazia, mas que o produto passada é nulo, deve lançar exception.")
    void deveLancarExceptionSeProdutoNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> verificarProdutoExisteNaWishlistInput.verificaProdutoExisteNaWishlist("1", null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id do produto.");
    }
}
