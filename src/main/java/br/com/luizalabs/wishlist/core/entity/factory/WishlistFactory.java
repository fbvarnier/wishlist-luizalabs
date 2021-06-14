package br.com.luizalabs.wishlist.core.entity.factory;

import br.com.luizalabs.wishlist.core.entity.Wishlist;

import java.util.List;

public interface WishlistFactory {

    Wishlist criar(String nome, String idCliente, List<String> idProdutos);
}
