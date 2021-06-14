package br.com.luizalabs.wishlist.gateway.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("cliente")
@Data
@AllArgsConstructor
public class ClienteDataModel {

    @Id
    private String id;
    private String nome;
}
