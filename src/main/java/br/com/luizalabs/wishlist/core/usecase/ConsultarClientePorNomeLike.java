package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarClienteResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarClientePorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.gateway.database.ClienteDS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarClientePorNomeLike implements ConsultarClientePorNomeLikeInput {

    private final ClienteDS clienteDS;

    @Override
    public ConsultarClienteResponseList consulta(String nome) {
        if (nome == null)
            throw new DadosFaltandoException("Nome não pode ser nulo.");

        var consultarClienteResponseList = new ConsultarClienteResponseList();
        clienteDS.buscaPorNomeLike(nome)
                .forEach(cliente -> consultarClienteResponseList.add(cliente.getId(), cliente.getNome()));

        return consultarClienteResponseList;
    }
}
