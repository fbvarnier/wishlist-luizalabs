package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutosPorWishlistResponse;

public interface ConsultarProdutosPorWishlistInput {

    ConsultarProdutosPorWishlistResponse consultaProdutosPorWishlist(String id);
}
