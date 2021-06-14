package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarWishlistPorId implements ConsultarWishlistPorIdInput {

    private final WishlistDS wishlistDS;

    @Override
    public ConsultarWishlistResponse consulta(String id) {
        if(StringUtils.isEmpty(id))
            throw new DadosFaltandoException("Id deve ser preenchida.");
        Optional<WishlistDataModel> wishlistDataModelOptional = wishlistDS.buscarWishlistPorId(id);
        if(wishlistDataModelOptional.isEmpty())
            throw new RegistroNaoEncontradoException("Wishlist não encontrada.");
        var dataModel = wishlistDataModelOptional.get();
        return new ConsultarWishlistResponse(dataModel.getId(), dataModel.getNome(), dataModel.getIdCliente(), dataModel.getIdProdutos());
    }
}
