package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutosPorWishlistResponse;
import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarProdutosPorWishlistInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import br.com.luizalabs.wishlist.gateway.database.model.ProdutoDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ConsultarProdutosPorWishlistTest {

    ConsultarProdutosPorWishlistInput consultarProdutosPorWishlistInput;

    @Mock
    private ProdutoDS produtoDS;

    @Mock
    private ConsultarWishlistPorId consultarWishlistPorId;

    @BeforeEach
    void init(){
        consultarProdutosPorWishlistInput = new ConsultarProdutosPorWishlist(produtoDS, consultarWishlistPorId);
    }

    @Test
    @DisplayName("Dado que id não é nulo ou vazio, que a wishlist exista e que exista produtos na wishlist, " +
            "deve retornar uma lista de produtos que existem na wishlist")
    void deveConsultar(){
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        ConsultarWishlistResponse wishlistResponse = new ConsultarWishlistResponse("1","nome", "1", produtos);

        List<ProdutoDataModel> produtoDataModelList = List.of(
                new ProdutoDataModel("4", "nome 4"),
                new ProdutoDataModel("5", "nome 5")
        );

        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(wishlistResponse);
        Mockito.when(produtoDS.buscarPorIdIn(produtos)).thenReturn(produtoDataModelList);

        ConsultarProdutosPorWishlistResponse consultaProdutosPorWishlist = consultarProdutosPorWishlistInput.consultaProdutosPorWishlist("1");

        Assertions.assertNotNull(consultaProdutosPorWishlist);
        Assertions.assertNotNull(consultaProdutosPorWishlist.getData());
        Assertions.assertEquals(2, consultaProdutosPorWishlist.getData().size());
    }
    @Test
    @DisplayName("Dado que o não existem produtos na wishlist, deve lançar exception.")
    void naoConsultarELancaExceptionQuandoWishlistNaoTiverProdutos(){
        ConsultarWishlistResponse wishlistResponse = new ConsultarWishlistResponse("1","nome", "1", List.of());

        Mockito.when(consultarWishlistPorId.consulta("1")).thenReturn(wishlistResponse);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarProdutosPorWishlistInput.consultaProdutosPorWishlist("1"))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("A wishlist não possui produtos.");
    }
    @Test
    @DisplayName("Dado que a id é nula, e deve lançar exception.")
    void naoDeveConsultarELancaExceptionParaIdNula(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarProdutosPorWishlistInput.consultaProdutosPorWishlist(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
    @Test
    @DisplayName("Dado que a id é vazia, e deve lançar exception.")
    void naoDeveConsultarELancaExceptionParaIdVazia(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarProdutosPorWishlistInput.consultaProdutosPorWishlist(""))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("É necessário informar o id da wishlist.");
    }
}
