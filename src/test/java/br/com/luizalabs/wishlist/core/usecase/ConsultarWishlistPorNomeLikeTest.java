package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
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

@ExtendWith(MockitoExtension.class)
class ConsultarWishlistPorNomeLikeTest {

    ConsultarWishlistPorNomeLikeInput consultarProdutosPorWishlistInput;

    @Mock
    private WishlistDS wishlistDS;

    @BeforeEach
    void init(){
        consultarProdutosPorWishlistInput = new ConsultarWishlistPorNomeLike(wishlistDS);
    }

    @Test
    @DisplayName("Dado que o nome não é nulo, deve retornar uma lista de wishlists com nome compátivel.")
    void deveConsultar(){
        List<WishlistDataModel> wishlistDataModelList = List.of(
                new WishlistDataModel("1", "lista 1", "2", List.of("4")),
                new WishlistDataModel("2", "lista 2", "3", List.of("5"))
        );

        Mockito.when(wishlistDS.buscarWishlistPorNomeLike("lista")).thenReturn(wishlistDataModelList);

        ConsultarWishlistResponseList wishlistResponseList = consultarProdutosPorWishlistInput.consulta("lista");

        Assertions.assertNotNull(wishlistResponseList);
        Assertions.assertNotNull(wishlistResponseList.getData());
        Assertions.assertEquals(2, wishlistResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o não existem wishlists, deve retornar vazio.")
    void deveConsultarERetornarVazio(){
        List<WishlistDataModel> wishlistDataModelList = List.of();

        Mockito.when(wishlistDS.buscarWishlistPorNomeLike("lista")).thenReturn(wishlistDataModelList);

        ConsultarWishlistResponseList wishlistResponseList = consultarProdutosPorWishlistInput.consulta("lista");

        Assertions.assertNotNull(wishlistResponseList);
        Assertions.assertNotNull(wishlistResponseList.getData());
        Assertions.assertEquals(0, wishlistResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que a nome é nulo, e deve lançar exception.")
    void naoDeveConsultarELancaExceptionParaNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarProdutosPorWishlistInput.consulta(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("Nome não pode ser nulo.");
    }
}
