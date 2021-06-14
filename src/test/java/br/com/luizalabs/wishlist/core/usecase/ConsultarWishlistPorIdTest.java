package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ConsultarWishlistPorIdTest {

    ConsultarWishlistPorIdInput consultarWishlistPorIdInput;

    @Mock
    private WishlistDS wishlistDS;

    @BeforeEach
    void init(){
        consultarWishlistPorIdInput = new ConsultarWishlistPorId(wishlistDS);
    }

    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que a wishlist com a id exista, deve retornar a wishlist.")
    void deveConsultar(){
        Mockito.when(wishlistDS.buscarWishlistPorId("1"))
                .thenReturn(Optional.of(new WishlistDataModel("1", "nome 1", "1", List.of("1"))));

        ConsultarWishlistResponse wishlistResponse = consultarWishlistPorIdInput.consulta("1");

        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertNotNull(wishlistResponse.getData());
        Assertions.assertEquals("1", wishlistResponse.getData().getId());
    }
    @Test
    @DisplayName("Dado que a id não é nula ou vazia e que a wishlist com a id não exista, deve lançar exception.")
    void deveLancarExceptionSeWishlistNaoExiste(){
        Mockito.when(wishlistDS.buscarWishlistPorId("1")).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarWishlistPorIdInput.consulta("1"))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Wishlist não encontrada.");
    }
    @Test
    @DisplayName("Dado que a id é nula , deve lançar exception.")
    void deveLancarExceptionParaIdNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarWishlistPorIdInput.consulta(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("Id deve ser preenchida.");
    }
}
