package br.com.luizalabs.wishlist.gateway.database.repository;


import br.com.luizalabs.wishlist.gateway.database.model.ClienteDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteDataModel, String> {

    List<ClienteDataModel> findByNomeLike(String nome);
}
