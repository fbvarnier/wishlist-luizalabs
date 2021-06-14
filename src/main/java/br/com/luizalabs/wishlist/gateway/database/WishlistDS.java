package br.com.luizalabs.wishlist.gateway.database;


import br.com.luizalabs.wishlist.core.entity.Wishlist;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;

import java.util.List;
import java.util.Optional;

public interface WishlistDS {
    Optional<WishlistDataModel> buscarWishlistPorIdEClienteId(String id, String clienteId);
    Optional<WishlistDataModel> buscarWishlistPorId(String id);
    List<WishlistDataModel> buscarWishlistPorNomeLike(String nome);
    WishlistDataModel save(String idWishlist, Wishlist wishlist);
}
