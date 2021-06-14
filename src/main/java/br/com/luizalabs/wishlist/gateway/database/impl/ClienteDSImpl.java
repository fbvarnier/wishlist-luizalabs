package br.com.luizalabs.wishlist.gateway.database.impl;

import br.com.luizalabs.wishlist.gateway.database.ClienteDS;
import br.com.luizalabs.wishlist.gateway.database.model.ClienteDataModel;
import br.com.luizalabs.wishlist.gateway.database.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteDSImpl implements ClienteDS {

    private final ClienteRepository clienteRepository;

    @Override
    public Optional<ClienteDataModel> buscaPorId(String id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<ClienteDataModel> buscaPorNomeLike(String nome) {
        return clienteRepository.findByNomeLike(nome);
    }
}
