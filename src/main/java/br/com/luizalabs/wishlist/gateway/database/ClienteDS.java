package br.com.luizalabs.wishlist.gateway.database;

import br.com.luizalabs.wishlist.gateway.database.model.ClienteDataModel;

import java.util.List;
import java.util.Optional;

public interface ClienteDS {
    Optional<ClienteDataModel> buscaPorId(String id);
    List<ClienteDataModel> buscaPorNomeLike(String nome);
}
