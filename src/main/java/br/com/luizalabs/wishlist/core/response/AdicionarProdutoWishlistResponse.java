package br.com.luizalabs.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class AdicionarProdutoWishlistResponse extends DefaultResponse {

    private Data data;

    @Getter
    public class Data {
        private String id;
        private String idCliente;
        private List<String> idProdutos;
    }

    public AdicionarProdutoWishlistResponse(String id, String idCliente, List<String> idProdutos) {
        data = new AdicionarProdutoWishlistResponse.Data();
        data.id = id;
        data.idCliente = idCliente;
        data.idProdutos = idProdutos;
    }
}
