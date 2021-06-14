package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.core.response.ConsultarProdutoResponseList;
import br.com.luizalabs.wishlist.core.response.DefaultResponse;
import br.com.luizalabs.wishlist.core.usecase.interfaces.ConsultarProdutoPorNomeLikeInput;
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

@Tag(name = "Operações do Produto")
@RequiredArgsConstructor
@RestController
public class ProdutoController {

    private final ConsultarProdutoPorNomeLikeInput consultarProdutoPorNomeLikeInput;

    @Operation(summary = "Busca um produto por 'like' do seu nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultarProdutoResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome do produto for nulo.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/produto/por-nome-like/{nome}")
    public ConsultarProdutoResponseList consultaProdutoPorNome(@Parameter(description = "Nome do produto.") @PathVariable String nome) {
        return consultarProdutoPorNomeLikeInput.consulta(nome);
    }
}
