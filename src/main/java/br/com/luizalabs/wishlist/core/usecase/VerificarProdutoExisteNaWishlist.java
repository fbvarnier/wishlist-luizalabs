package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.response.VerificarProdutoExisteNaWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorIdInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarProdutoExisteNaWishlistInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class VerificarProdutoExisteNaWishlist implements VerificarProdutoExisteNaWishlistInput {

    private final ConsultarWishlistPorIdInput consultarWishlistPorId;

    @Override
    public VerificarProdutoExisteNaWishlistResponse verificaProdutoExisteNaWishlist(String idWishlist, String idProduto) {
        if(StringUtils.isEmpty(idWishlist))
            throw new DadosFaltandoException("É necessário informar o id da wishlist.");
        if(StringUtils.isEmpty(idProduto))
            throw new DadosFaltandoException("É necessário informar o id do produto.");

        ConsultarWishlistResponse wishlistResponse = consultarWishlistPorId.consulta(idWishlist);
        if(CollectionUtils.isEmpty(wishlistResponse.getData().getIdProdutos()))
            return new VerificarProdutoExisteNaWishlistResponse(false);

        return new VerificarProdutoExisteNaWishlistResponse(wishlistResponse.getData().getIdProdutos()
                .stream()
                .anyMatch(id -> id.equals(idProduto)));
    }
}
