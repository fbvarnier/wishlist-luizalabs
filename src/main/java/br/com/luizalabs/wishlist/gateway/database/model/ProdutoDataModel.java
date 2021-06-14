package br.com.luizalabs.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("produto")
@Data
@AllArgsConstructor
public class ProdutoDataModel {

    @Id
    private String id;
    private String nome;
}
