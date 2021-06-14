package br.com.luizalabs.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

@Data
public class AdicionarNovaWishlistResponse extends DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String nome;
        private String idCliente;
    }

    public AdicionarNovaWishlistResponse(String id, String nome, String idCliente) {
        data = new Data();
        data.id = id;
        data.nome = nome;
        data.idCliente = idCliente;
    }

}
