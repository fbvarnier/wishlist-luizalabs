package br.com.luizalabs.wishlist.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
@AllArgsConstructor
public class ConsultarWishlistResponse extends DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String nome;
        private String idCliente;
        private List<String> idProdutos;
    }

    public ConsultarWishlistResponse(String id, String nome,String idCliente, List<String> idProdutos) {
        data = new Data();
        data.id = id;
        data.nome = nome;
        data.idCliente = idCliente;
        data.idProdutos = idProdutos;
    }

}
