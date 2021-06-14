package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutoResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarProdutoPorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.gateway.database.ProdutoDS;
import br.com.luizalabs.wishlist.gateway.database.model.ProdutoDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ConsultarProdutoPorNomeLikeTest {

    ConsultarProdutoPorNomeLikeInput consultarClientePorNomeLikeInput;

    @Mock
    private ProdutoDS produtoDS;

    @BeforeEach
    void init(){
        consultarClientePorNomeLikeInput = new ConsultarProdutoPorNomeLike(produtoDS);
    }

    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio e que algum produto exista, deve retornar a lista de produtos com nome compátivel.")
    void deveConsultar(){
        List<ProdutoDataModel> produtos = List.of(
                new ProdutoDataModel("1", "nome 1"),
                new ProdutoDataModel("2", "nonome"),
                new ProdutoDataModel("3", "nomemo"));
        Mockito.when(produtoDS.buscaPorNomeLike("nome")).thenReturn(produtos);

        ConsultarProdutoResponseList produtoResponseList = consultarClientePorNomeLikeInput.consulta("nome");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(3, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio, mas não existe produto com o nome compátivel, " +
            "deve retornar a lista de produtos vazia, mas nao nula.")
    void deveConsultarERestornarVazio(){
        List<ProdutoDataModel> produtos = List.of();
        Mockito.when(produtoDS.buscaPorNomeLike("nome")).thenReturn(produtos);

        ConsultarProdutoResponseList produtoResponseList = consultarClientePorNomeLikeInput.consulta("nome");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(0, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é vazio e deve trazer todos os produtos da base.")
    void deveConsultarComNomeVazio(){
        List<ProdutoDataModel> produtos = List.of(
                new ProdutoDataModel("1", "nome 1"),
                new ProdutoDataModel("2", "aaaa"),
                new ProdutoDataModel("3", "bbbb"));
        Mockito.when(produtoDS.buscaPorNomeLike("")).thenReturn(produtos);

        ConsultarProdutoResponseList produtoResponseList = consultarClientePorNomeLikeInput.consulta("");

        Assertions.assertNotNull(produtoResponseList);
        Assertions.assertNotNull(produtoResponseList.getData());
        Assertions.assertEquals(3, produtoResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é nulo deve lançar uma exception.")
    void naoDeveConsultarComNomeNulo(){
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> consultarClientePorNomeLikeInput.consulta(null))
                .isInstanceOf(DadosFaltandoException.class)
                .hasMessage("Nome não pode ser nulo.");
    }
}
