package br.com.luizalabs.wishlist.core.response;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConsultarClienteResponseList extends DefaultResponse {

    private List<Data> data = new ArrayList<>();

    @Getter
    public class Data {
        private String id;
        private String nome;
    }

    public ConsultarClienteResponseList(String id, String nome){
        this.add(id, nome);
    }
    public ConsultarClienteResponseList(){}

    public void add(String id, String nome){
        var dados = new Data();
        dados.id = id;
        dados.nome = nome;
        this.data.add(dados);
    }
}
