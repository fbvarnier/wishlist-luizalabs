package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutoResponseList;

public interface ConsultarProdutoPorNomeLikeInput {
    ConsultarProdutoResponseList consulta(String nome);
}
