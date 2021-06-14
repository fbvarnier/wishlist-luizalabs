package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.Wishlist;
import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.RemoverProdutoDaWishlistPorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RemoverProdutoDaWishlistPorIdTest {

    RemoverProdutoDaWishlistPorIdInput removerProdutoDaWishlistPorIdInput;

    @Mock
    private WishlistDS wishlistDS;
    @Mock
    private ConsultarWishlistPorId consultarWishlistPorId;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @Captor
    private ArgumentCaptor<Wishlist> wishlistArgumentCaptor;

    @BeforeEach
    void init(){
        removerProdutoDaWishlistPorIdInput = new RemoverProdutoDaWishlistPorId(wishlistDS, consultarWishlistPorId, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto passados não são nulos, e que a wishlist existe e tem o produto passado," +
            " deve remover o produto passado da wishlist.")
    void deveRemoverProdutoDaWishlist(){
        List<String> produtos = new ArrayList<>();
        produtos.add("1");
        produtos.add("2");
        var wishlistResponse = new ConsultarWishlistResponse("1", "nome", "1", produtos);
        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(wishlistResponse);

        removerProdutoDaWishlistPorIdInput.removeDaWishlistPorId("1", "1");

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), wishlistArgumentCaptor.capture());
        Wishlist wishlistSalva = wishlistArgumentCaptor.getValue();
        Assertions.assertNotNull(wishlistSalva);
        Assertions.assertNotNull(wishlistSalva.getIdProdutos());
        Assertions.assertEquals(1, wishlistSalva.getIdProdutos().size());
    }

    @Test
    @DisplayName("Dado que a wishlist e o produto passados não são nulos, e que a wishlist existe e não tem o produto passado," +
            " deve lançar exception.")
    void deveLancarExceptionSeWishlistNaoTemProdutos(){
        var wishlistResponse = new ConsultarWishlistResponse("1", "nome", "1", List.of());
        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(wishlistResponse);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removerProdutoDaWishlistPorIdInput.removeDaWishlistPorId("1", "1"))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Produto não encontrado na wishlist.");
    }
    @Test
    @DisplayName("Dado que o produto passado é nulo, deve lançar uma exception.")
    void deveLancarExceptionParaProdutoNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removerProdutoDaWishlistPorIdInput.removeDaWishlistPorId("1", null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id do produto.");
    }
    @Test
    @DisplayName("Dado que a wishlist passado é nula, deve lançar uma exception.")
    void deveLancarExceptionParaWishlistNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> removerProdutoDaWishlistPorIdInput.removeDaWishlistPorId(null, "1"))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
}
