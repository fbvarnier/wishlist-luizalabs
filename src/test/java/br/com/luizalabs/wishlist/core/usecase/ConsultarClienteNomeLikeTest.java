package br.com.luizalabs.wishlist.core.usecase;

import br.com.luizalabs.wishlist.core.response.ConsultarClienteResponseList;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarClientePorNomeLikeInput;
import br.com.luizalabs.wishlist.exception.DadosFaltandoException;
import br.com.luizalabs.wishlist.gateway.database.ClienteDS;
import br.com.luizalabs.wishlist.gateway.database.model.ClienteDataModel;
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
class ConsultarClienteNomeLikeTest {

    ConsultarClientePorNomeLikeInput consultarClientePorNomeLikeInput;

    @Mock
    private ClienteDS clienteDS;

    @BeforeEach
    void init(){
        consultarClientePorNomeLikeInput = new ConsultarClientePorNomeLike(clienteDS);
    }

    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio e que algum cliente exista, deve retornar a lista de cliente com nome compátivel.")
    void deveConsultar(){
        List<ClienteDataModel> cliente = List.of(
                new ClienteDataModel("1", "nome 1"),
                new ClienteDataModel("2", "nonome"),
                new ClienteDataModel("3", "nomemo"));
        Mockito.when(clienteDS.buscaPorNomeLike("nome")).thenReturn(cliente);

        ConsultarClienteResponseList clienteResponseList = consultarClientePorNomeLikeInput.consulta("nome");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(3, clienteResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome não é nulo ou vazio, mas não existe cliente com o nome compátivel, " +
            "deve retornar a lista de clientes vazia, mas nao nula.")
    void deveConsultarERestornarVazio(){
        List<ClienteDataModel> cliente = List.of();
        Mockito.when(clienteDS.buscaPorNomeLike("nome")).thenReturn(cliente);

        ConsultarClienteResponseList clienteResponseList = consultarClientePorNomeLikeInput.consulta("nome");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(0, clienteResponseList.getData().size());
    }
    @Test
    @DisplayName("Dado que o nome é vazio e deve trazer todos os clientes da base.")
    void deveConsultarComNomeVazio(){
        List<ClienteDataModel> cliente = List.of(
                new ClienteDataModel("1", "nome 1"),
                new ClienteDataModel("2", "aaaa"),
                new ClienteDataModel("3", "bbbb"));
        Mockito.when(clienteDS.buscaPorNomeLike("")).thenReturn(cliente);

        ConsultarClienteResponseList clienteResponseList = consultarClientePorNomeLikeInput.consulta("");

        Assertions.assertNotNull(clienteResponseList);
        Assertions.assertNotNull(clienteResponseList.getData());
        Assertions.assertEquals(3, clienteResponseList.getData().size());
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
