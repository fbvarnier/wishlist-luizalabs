package br.com.luizalabs.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("wishlist")
@Data
@AllArgsConstructor
public class WishlistDataModel {

    @Id
    private String id;
    private String nome;
    private String idCliente;
    private List<String> idProdutos;
}
