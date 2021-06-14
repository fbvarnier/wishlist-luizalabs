package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarProdutoExistePorIdInput;
import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificarProdutoExistePorId implements VerificarProdutoExistePorIdInput {

    private final ProdutoDS produtoDS;

    @Override
    public boolean verificaExistenciaProdutoPorId(String id) {
        if (id == null)
            return false;
        return produtoDS.buscarPorId(id).isPresent();
    }
}
