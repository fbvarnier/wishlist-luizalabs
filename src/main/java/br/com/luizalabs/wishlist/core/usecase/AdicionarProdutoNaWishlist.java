package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.request.AdicionarProdutoWishlistRequest;
import br.com.luizalabs.wishlist.core.response.AdicionarProdutoWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.AdicionarProdutoNaWishlistInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarProdutoExistePorIdInput;
import br.com.luizalabs.wishlist.exception.OperacaoInvalidaException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdicionarProdutoNaWishlist implements AdicionarProdutoNaWishlistInput {

    private final WishlistDS wishlistDS;

    private final VerificarProdutoExistePorIdInput consultarProduto;
    private final WishlistFactory wishlistFactory;

    @Override
    public AdicionarProdutoWishlistResponse adicionar(AdicionarProdutoWishlistRequest wishlistRequest) {
        Optional<WishlistDataModel> optionalWishlistDataModel = wishlistDS.buscarWishlistPorIdEClienteId(
                wishlistRequest.getId(), wishlistRequest.getIdCliente());
        if (optionalWishlistDataModel.isEmpty())
            throw new RegistroNaoEncontradoException("Wishlist não existe.");
        var wishlistDataModel = optionalWishlistDataModel.get();

        if(!consultarProduto.verificaExistenciaProdutoPorId(wishlistRequest.getIdProduto()))
            throw new RegistroNaoEncontradoException("Produto não existe.");

        var wishlist = wishlistFactory.criar(wishlistDataModel.getNome(), wishlistDataModel.getIdCliente(), wishlistDataModel.getIdProdutos());

        if(wishlist.isWishlistCheia())
            throw new OperacaoInvalidaException("Wishlist já está cheia.");

        wishlist.getIdProdutos().add(wishlistRequest.getIdProduto());

        wishlistDS.save(wishlistDataModel.getId(), wishlist);
        return new AdicionarProdutoWishlistResponse(wishlistDataModel.getId(),
                wishlist.getIdCliente(), wishlist.getIdProdutos());
    }
}
