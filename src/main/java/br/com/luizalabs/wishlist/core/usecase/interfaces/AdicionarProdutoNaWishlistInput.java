package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.request.AdicionarProdutoWishlistRequest;
import br.com.luizalabs.wishlist.core.response.AdicionarProdutoWishlistResponse;

public interface AdicionarProdutoNaWishlistInput {

    AdicionarProdutoWishlistResponse adicionar(AdicionarProdutoWishlistRequest wishlistRequest);
}
