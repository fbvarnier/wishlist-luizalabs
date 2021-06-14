package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutoResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarProdutoPorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultarProdutoPorNomeLike implements ConsultarProdutoPorNomeLikeInput {

    private final ProdutoDS produtoDS;

    @Override
    public ConsultarProdutoResponseList consulta(String nome) {
        if (nome == null)
            throw new DadosFaltandoException("Nome não pode ser nulo.");

        var consultarProdutoResponseList = new ConsultarProdutoResponseList();
        produtoDS.buscaPorNomeLike(nome)
            .forEach(produto -> consultarProdutoResponseList.add(produto.getId(), produto.getNome()));

        return consultarProdutoResponseList;
    }
}
