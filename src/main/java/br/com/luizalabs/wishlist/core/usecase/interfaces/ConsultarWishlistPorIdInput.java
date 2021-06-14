package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;

public interface ConsultarWishlistPorIdInput {
    ConsultarWishlistResponse consulta(String id);
}
