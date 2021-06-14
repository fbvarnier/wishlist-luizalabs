package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.core.response.ConsultarClienteResponseList;
import br.com.luizalabs.wishlist.core.response.DefaultResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarClientePorNomeLikeInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Operações do Cliente")
@RequiredArgsConstructor
@RestController
public class ClienteController {

    private final ConsultarClientePorNomeLikeInput consultarClientePorNomeLikeInput;

    @Operation(summary = "Busca um cliente por 'like' do seu nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultarClienteResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome do cliente não for nulo.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/cliente/por-nome-like/{nome}")
    public ConsultarClienteResponseList consultaClientePorNome(@Parameter(description = "Nome do cliente.") @PathVariable String nome) {
        return consultarClientePorNomeLikeInput.consulta(nome);
    }
}
