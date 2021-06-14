package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutosPorWishlistResponse;
import br.com.luizalabs.wishlist.core.response.ConsultarWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarProdutosPorWishlistInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarWishlistPorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class ConsultarProdutosPorWishlist implements ConsultarProdutosPorWishlistInput {

    private final ProdutoDS produtoDS;
    private final ConsultarWishlistPorIdInput consultarWishlistPorId;

    @Override
    public ConsultarProdutosPorWishlistResponse consultaProdutosPorWishlist(String id) {
        if(StringUtils.isEmpty(id))
            throw new DadosFaltandoException("É necessário informar o id da wishlist.");

        ConsultarWishlistResponse wishlistResponse = consultarWishlistPorId.consulta(id);
        if(CollectionUtils.isEmpty(wishlistResponse.getData().getIdProdutos()))
            throw new RegistroNaoEncontradoException("A wishlist não possui produtos.");

        var response = new ConsultarProdutosPorWishlistResponse();
        produtoDS.buscarPorIdIn(wishlistResponse.getData().getIdProdutos())
                .forEach(produto -> response.add(produto.getId(), produto.getNome()));
        return response;
    }
}
