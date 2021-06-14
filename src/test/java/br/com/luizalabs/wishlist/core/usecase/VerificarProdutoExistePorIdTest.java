package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import br.com.luizalabs.wishlist.gateway.database.model.ProdutoDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VerificarProdutoExistePorIdTest {
    @InjectMocks
    private VerificarProdutoExistePorId consultarProduto;

    @Mock
    private ProdutoDS produtoDS;

    @Test
    @DisplayName("Dado que o produto existe deve retornar true.")
    void deveRetornarTrueSeExisteProduto(){

        Mockito.when(produtoDS.buscarPorId("1")).thenReturn(Optional.of(new ProdutoDataModel("1", "produto")));

        Assertions.assertTrue(consultarProduto.verificaExistenciaProdutoPorId("1"));
    }
    @Test
    @DisplayName("Dado que o nao produto existe deve retornar false.")
    void deveRetornarFalseSeNaoExisteProduto(){

        Mockito.when(produtoDS.buscarPorId("1")).thenReturn(Optional.empty());

        Assertions.assertFalse(consultarProduto.verificaExistenciaProdutoPorId("1"));
    }
    @Test
    @DisplayName("Dado que o parametro id passado sejua nullo, deve retornar false.")
    void deveRetornarFalseQuandoPassadoNull(){
        Assertions.assertFalse(consultarProduto.verificaExistenciaProdutoPorId(null));
    }
}
