package br.com.luizalabs.wishlist.core.usecase.interfaces;

import br.com.luizalabs.wishlist.core.response.ConsultarClienteResponseList;

public interface ConsultarClientePorNomeLikeInput {

    ConsultarClienteResponseList consulta(String id);
}
