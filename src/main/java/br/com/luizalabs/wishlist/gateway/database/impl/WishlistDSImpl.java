package br.com.luizalabs.wishlist.gateway.database.impl;

import br.com.luizalabs.wishlist.core.entity.Wishlist;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import br.com.luizalabs.wishlist.gateway.database.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishlistDSImpl implements WishlistDS {

    private final WishlistRepository wishlistRepository;

    @Override
    public Optional<WishlistDataModel> buscarWishlistPorIdEClienteId(String id, String clienteId) {
        return wishlistRepository.findByIdAndIdCliente(id, clienteId);
    }

    @Override
    public Optional<WishlistDataModel> buscarWishlistPorId(String id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public List<WishlistDataModel> buscarWishlistPorNomeLike(String nome) {
        return wishlistRepository.findByNomeLike(nome);
    }

    @Override
    public WishlistDataModel save(String idWishlist, Wishlist wishlist) {
        var wishlistDataModel = new WishlistDataModel(idWishlist, wishlist.getNome(), wishlist.getIdCliente(), wishlist.getIdProdutos());
        return wishlistRepository.save(wishlistDataModel);
    }
}
