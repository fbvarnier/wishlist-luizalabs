package br.com.luizalabs.wishlist.gateway.database.repository;


import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<WishlistDataModel, String> {

    Optional<WishlistDataModel> findByIdAndIdCliente(String id, String idCliente);
    List<WishlistDataModel> findByNomeLike(String nome);
}
