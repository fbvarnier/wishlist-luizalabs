package br.com.luizalabs.wishlist.core.entity.factory.impl;

import br.com.luizalabs.wishlist.core.entity.Wishlist;
import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.entity.impl.WishlistImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WishlistFactoryImpl implements WishlistFactory {
    @Override
    public Wishlist criar(String nome, String idCliente, List<String> idProdutos) {
        return new WishlistImpl(nome, idCliente, idProdutos);
    }
}
