package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponseList;

public interface ConsultarWishlistPorNomeLikeInput {
    ConsultarWishlistResponseList consulta(String nome);
}
