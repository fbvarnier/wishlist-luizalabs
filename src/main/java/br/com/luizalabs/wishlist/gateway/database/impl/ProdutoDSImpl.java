package br.com.luizalabs.wishlist.gateway.database.impl;

import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import br.com.luizalabs.wishlist.gateway.database.model.ProdutoDataModel;
import br.com.luizalabs.wishlist.gateway.database.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class ProdutoDSImpl implements ProdutoDS {

    private final ProdutoRepository produtoRepository;

    @Override
    public Optional<ProdutoDataModel> buscarPorId(String idProduto) {
        return produtoRepository.findById(idProduto);
    }

    @Override
    public List<ProdutoDataModel> buscarPorIdIn(List<String> ids) {
        return StreamSupport.stream(produtoRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoDataModel> buscaPorNomeLike(String nome) {
        return produtoRepository.findByNomeLike(nome);
    }
}
