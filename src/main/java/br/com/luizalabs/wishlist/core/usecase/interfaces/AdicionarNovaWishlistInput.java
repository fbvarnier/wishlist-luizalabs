package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.AdicionarNovaWishlistResponse;

public interface AdicionarNovaWishlistInput {

    AdicionarNovaWishlistResponse adicionar(String nome, String idCliente);
}
