package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorIdInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.RemoverProdutoDaWishlistPorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RemoverProdutoDaWishlistPorId implements RemoverProdutoDaWishlistPorIdInput {

    private final WishlistDS wishlistDS;
    private final ConsultarWishlistPorIdInput consultarWishlistPorIdInput;
    private final WishlistFactory wishlistFactory;

    @Override
    public void removeDaWishlistPorId(String idWishlist, String idProduto) {
        if(StringUtils.isEmpty(idWishlist))
            throw new DadosFaltandoException("É necessário informar o id da wishlist.");
        if(StringUtils.isEmpty(idProduto))
            throw new DadosFaltandoException("É necessário informar o id do produto.");

        ConsultarWishlistResponse wishlistResponse = consultarWishlistPorIdInput.consulta(idWishlist);

        List<String> idProdutos = wishlistResponse.getData().getIdProdutos();
        if (!idProdutos.contains(idProduto))
            throw new RegistroNaoEncontradoException("Produto não encontrado na wishlist.");

        idProdutos.remove(idProduto);

        var wishlist = wishlistFactory.criar(wishlistResponse.getData().getNome(), wishlistResponse.getData().getIdCliente(), idProdutos);
        wishlistDS.save(wishlistResponse.getData().getId(), wishlist);
    }
}
