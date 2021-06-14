package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultarWishlistPorNomeLike implements ConsultarWishlistPorNomeLikeInput {

    private final WishlistDS wishlistDS;

    @Override
    public ConsultarWishlistResponseList consulta(String nome) {
        if (nome == null)
            throw new DadosFaltandoException("Nome não pode ser nulo.");

        var consultarWishlistResponseList = new ConsultarWishlistResponseList();
        wishlistDS.buscarWishlistPorNomeLike(nome)
                .forEach(wish -> consultarWishlistResponseList.add(wish.getId(), wish.getNome(), wish.getIdCliente(), wish.getIdProdutos()));
        return consultarWishlistResponseList;
    }
}
