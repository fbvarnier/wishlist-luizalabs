package br.com.luizalabs.wishlist.gateway.database;

import br.com.luizalabs.wishlist.gateway.database.model.ProdutoDataModel;

import java.util.List;
import java.util.Optional;

public interface ProdutoDS {
    Optional<ProdutoDataModel> buscarPorId(String idProduto);
    List<ProdutoDataModel> buscarPorIdIn(List<String> ids);
    List<ProdutoDataModel> buscaPorNomeLike(String nome);
}
