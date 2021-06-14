package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.usecase.interfaces.VerificarClienteExistePorIdInput;
import br.com.luizalabs.wishlist.gateway.database.ClienteDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificarClienteExistePorId implements VerificarClienteExistePorIdInput {

    private final ClienteDS clienteDS;

    @Override
    public boolean consultaExistenciaClientePorId(String id) {
        if (id == null)
            return false;
        return clienteDS.buscaPorId(id).isPresent();
    }
}
