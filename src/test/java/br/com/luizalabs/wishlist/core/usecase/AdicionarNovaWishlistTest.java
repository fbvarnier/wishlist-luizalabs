package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import br.com.luizalabs.wishlist.core.response.AdicionarNovaWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.AdicionarNovaWishlistInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarClienteExistePorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdicionarNovaWishlistTest {

    AdicionarNovaWishlistInput adicionarNovaWishlist;

    @Mock
    private WishlistDS wishlistDS;

    @Mock
    private VerificarClienteExistePorIdInput consultarCliente;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @BeforeEach
    void init(){
        adicionarNovaWishlist = new AdicionarNovaWishlist(wishlistDS, consultarCliente, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que o nome e o id do cliente não sejam nulos, vazios ou em branco, e que o cliente exista, " +
            "deve adicionar uma nova wishlist.")
    void deveAdicionarNovaWishlist(){
        WishlistDataModel wishlistDataModel = new WishlistDataModel("1","lista", "1", List.of());

        Mockito.when(wishlistDS.save(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(wishlistDataModel);
        Mockito.when(consultarCliente.consultaExistenciaClientePorId("1")).thenReturn(true);

        AdicionarNovaWishlistResponse wishlistResponse = adicionarNovaWishlist.adicionar("lista", "1");

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(wishlistResponse);
    }
    @Test
    @DisplayName("Dado que o nome passado é nulo, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarNovaWishlist.adicionar(null, "1"))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o nome passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoNomeEmBranco(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarNovaWishlist.adicionar("  ", "1"))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente passado é nulo, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarNovaWishlist.adicionar("lista", null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteEmBranco(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarNovaWishlist.adicionar("lista", " "))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
    }
    @Test
    @DisplayName("Dado que o id cliente não existe passado está em branco, nenhuma wishlist deve ser adicionada, e deve lançar exception.")
    void naoDeveAdicionarNovaWishlistQuandoIdClienteNaoExiste(){
        Mockito.when(consultarCliente.consultaExistenciaClientePorId("1")).thenReturn(false);
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarNovaWishlist.adicionar("lista", "1"))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Cliente não encontrado.");
    }
}
