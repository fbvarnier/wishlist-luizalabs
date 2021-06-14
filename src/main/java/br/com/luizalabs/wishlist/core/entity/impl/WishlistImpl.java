package br.com.luizalabs.wishlist.core.entity.impl;

import br.com.luizalabs.wishlist.core.entity.Wishlist;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WishlistImpl implements Wishlist {

    private static final int LIMITE_PRODUTOS = 20;

    private final String nome;
    private final String idCliente;
    private final List<String> idProdutos;

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getIdCliente() {
        return this.idCliente;
    }

    @Override
    public List<String> getIdProdutos() {
        return this.idProdutos;
    }

    @Override
    public boolean isWishlistCheia() {
        if (this.idProdutos == null)
            return false;
        return this.idProdutos.size() >= LIMITE_PRODUTOS;
    }
}
