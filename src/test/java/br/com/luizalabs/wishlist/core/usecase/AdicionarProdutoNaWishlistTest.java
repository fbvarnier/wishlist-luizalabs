package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.entity.factory.impl.WishlistFactoryImpl;
import br.com.luizalabs.wishlist.core.request.AdicionarProdutoWishlistRequest;
import br.com.luizalabs.wishlist.core.response.AdicionarProdutoWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.AdicionarProdutoNaWishlistInput;
import br.com.luizalabs.wishlist.exception.OperacaoInvalidaException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class AdicionarProdutoNaWishlistTest {

    AdicionarProdutoNaWishlistInput adicionarProdutoNaWishlist;

    @Mock
    private WishlistDS wishlistDS;
    @Mock
    private VerificarProdutoExistePorId consultarProduto;

    private WishlistFactory wishlistFactory = new WishlistFactoryImpl();

    @BeforeEach
    void init(){
        adicionarProdutoNaWishlist = new AdicionarProdutoNaWishlist(wishlistDS, consultarProduto, wishlistFactory);
    }

    @Test
    @DisplayName("Dado que wishlist, cliente e o produto existam, " +
            "e a wishlis não esteja cheia, deve adicionar o produto a whishlist")
    void deveAdicionarProdutoAWishlist(){
        AdicionarProdutoWishlistRequest wishlistRequest = new AdicionarProdutoWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getIdCliente(), produtos);

        Mockito.when(wishlistDS.buscarWishlistPorIdEClienteId(wishlistRequest.getId(),
                wishlistRequest.getIdCliente())).thenReturn(Optional.of(wishlistDataModel));

        Mockito.when(consultarProduto.verificaExistenciaProdutoPorId("3")).thenReturn(true);

        AdicionarProdutoWishlistResponse wishlistResponse = adicionarProdutoNaWishlist.adicionar(wishlistRequest);

        Mockito.verify(wishlistDS).save(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(wishlistResponse);
        Assertions.assertNotNull(wishlistResponse.getData().getIdProdutos());
        Assertions.assertEquals(3, wishlistResponse.getData().getIdProdutos().size());
    }
    @Test
    @DisplayName("Dado que a wishlist não existe, nenhum produto deve ser adicionado, e deve lançar exception.")
    void naoDeveAdicionarProdutoAWishlistQuandoNaoExisteWishlist(){
        AdicionarProdutoWishlistRequest wishlistRequest = new AdicionarProdutoWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        produtos.add("4");
        produtos.add("5");
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getIdCliente(), produtos);

        Mockito.when(wishlistDS.buscarWishlistPorIdEClienteId(wishlistRequest.getId(),
                wishlistRequest.getIdCliente())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarProdutoNaWishlist.adicionar(wishlistRequest))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Wishlist não existe.");
    }
    @Test
    @DisplayName("Dado que o produto não existe, deve lançar exception.")
    void naoDeveAdicionarSeProdutoNaoExiste(){
        AdicionarProdutoWishlistRequest wishlistRequest = new AdicionarProdutoWishlistRequest("1","2", "3");
        List<String> produtos = new ArrayList<>();
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getIdCliente(), produtos);

        Mockito.when(wishlistDS.buscarWishlistPorIdEClienteId(wishlistRequest.getId(),
                wishlistRequest.getIdCliente())).thenReturn(Optional.of(wishlistDataModel));
        Mockito.when(consultarProduto.verificaExistenciaProdutoPorId("3")).thenReturn(false);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarProdutoNaWishlist.adicionar(wishlistRequest))
                .isInstanceOf(RegistroNaoEncontradoException.class)
                .hasMessage("Produto não existe.");
    }
    @Test
    @DisplayName("Dado que a wishlist e o produto existam, e que a wishlist possui 20 produtos, " +
            "não deve adicionar mais um produto, e deve lançar exception.")
    void naoDeveAdicionarOutroProdutoComWishlistCheia(){
        AdicionarProdutoWishlistRequest wishlistRequest = new AdicionarProdutoWishlistRequest("1","2", "21");
        List<String> produtos = IntStream.range(0, 20)
                .mapToObj(String::valueOf).collect(Collectors.toList());
        WishlistDataModel wishlistDataModel = new WishlistDataModel(wishlistRequest.getId(),"lista",
                wishlistRequest.getIdCliente(), produtos);

        Mockito.when(wishlistDS.buscarWishlistPorIdEClienteId(wishlistRequest.getId(),
                wishlistRequest.getIdCliente())).thenReturn(Optional.of(wishlistDataModel));
        Mockito.when(consultarProduto.verificaExistenciaProdutoPorId("21")).thenReturn(true);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> adicionarProdutoNaWishlist.adicionar(wishlistRequest))
                .isInstanceOf(OperacaoInvalidaException.class)
                .hasMessage("Wishlist já está cheia.");
    }
}
