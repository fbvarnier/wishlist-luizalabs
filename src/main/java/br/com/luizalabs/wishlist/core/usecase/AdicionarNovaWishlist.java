package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.response.AdicionarNovaWishlistResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.AdicionarNovaWishlistInput;
import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarClienteExistePorIdInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.exception.RegistroNaoEncontradoException;
import br.com.luizalabs.wishlist.gateway.database.WishlistDS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdicionarNovaWishlist implements AdicionarNovaWishlistInput {

    private final WishlistDS wishlistDS;
    private final VerificarClienteExistePorIdInput consultarCliente;
    private final WishlistFactory wishlistFactory;

    @Override
    public AdicionarNovaWishlistResponse adicionar(String nome, String idCliente) {
        if (nome == null || nome.isBlank())
            throw new DadosFaltandoException("É obrigário preenchimento do nome para adicionar uma nova wishlist.");
        if (idCliente == null || idCliente.isBlank())
            throw new DadosFaltandoException("É obrigário informar a id do cliente para adicionar uma nova wishlist.");
        if(!consultarCliente.consultaExistenciaClientePorId(idCliente))
            throw new RegistroNaoEncontradoException("Cliente não encontrado.");

        var wishlist = wishlistFactory.criar(nome, idCliente, List.of());
        var wishlistDataModel = wishlistDS.save(null, wishlist);

        return new AdicionarNovaWishlistResponse(wishlistDataModel.getId(),
                wishlistDataModel.getNome(), wishlistDataModel.getIdCliente());
    }
}
