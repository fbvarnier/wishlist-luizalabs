package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.VerificarProdutoExisteNaWishlistResponse;

public interface VerificarProdutoExisteNaWishlistInput {

    VerificarProdutoExisteNaWishlistResponse verificaProdutoExisteNaWishlist(String idWishlist, String idProduto);
}
