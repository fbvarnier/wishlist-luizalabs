package br.com.luizalabs.wishlist.core.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ConsultarWishlistResponseList extends DefaultResponse {

    private List<Data> data = new ArrayList<>();

    @Getter
    public class Data {
        private String id;
        private String nome;
        private String idCliente;
        private List<String> idProdutos;
    }

    public ConsultarWishlistResponseList(String id, String nome,String idCliente, List<String> idProdutos){
        this.add(id, nome, idCliente, idProdutos);
    }

    public void add(String id, String nome,String idCliente, List<String> idProdutos){
        var dados = new Data();
        dados.id = id;
        dados.nome = nome;
        dados.idCliente = idCliente;
        dados.idProdutos = idProdutos;
        this.data.add(dados);
    }
}
