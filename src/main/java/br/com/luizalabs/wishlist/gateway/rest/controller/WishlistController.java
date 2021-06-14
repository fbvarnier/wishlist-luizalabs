package br.com.luizalabs.wishlist.gateway.rest.controller;

import br.com.luizalabs.wishlist.core.request.AdicionarNovaWishlistRequest;
import br.com.luizalabs.wishlist.core.request.AdicionarProdutoWishlistRequest;
import br.com.luizalabs.wishlist.core.response.*;
import br.com.luizalabs.wishlist.core.usecase.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Operações da Wishlist")
@RequiredArgsConstructor
@RestController("/wishlist")
public class WishlistController {

    private final AdicionarProdutoNaWishlistInput adicionarProdutoNaWishlistInput;
    private final AdicionarNovaWishlistInput adicionarNovaWishlistInput;
    private final ConsultarWishlistPorNomeLikeInput consultarWishlistPorNomeLikeInput;
    private final ConsultarWishlistPorIdInput consultarWishlistPorIdInput;
    private final ConsultarProdutosPorWishlistInput consultarProdutosPorWishlistInput;
    private final VerificarProdutoExisteNaWishlistInput verificarProdutoExisteNaWishlistInput;
    private final RemoverProdutoDaWishlistPorIdInput removerProdutoDaWishlistPorIdInput;

    @Operation(summary = "Adiciona um produto a uma wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto adicionado com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdicionarProdutoWishlistResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Produto ou wishlist não encontrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Wishlist cheia.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @PostMapping("/add-produto")
    public AdicionarProdutoWishlistResponse adicionarProdutoNaWishlist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para um produto a uma wishlist.", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdicionarProdutoWishlistRequest.class)))
            @RequestBody AdicionarProdutoWishlistRequest request){
        return adicionarProdutoNaWishlistInput.adicionar(request);
    }


    @Operation(summary = "Busca os produtos de uma wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultarProdutosPorWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id da wishlist não for informada.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se a wishlist não possui produtos.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/buscar/produtos-na-wishlist/{idWishlist}")
    public ConsultarProdutosPorWishlistResponse consultaProdutosPorWishlist(@Parameter(description = "Id da wishlist.") @PathVariable String idWishlist) {
        return consultarProdutosPorWishlistInput.consultaProdutosPorWishlist(idWishlist);
    }


    @Operation(summary = "Verifica se um produto existe uma determinada wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerificarProdutoExisteNaWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id do produto ou da wishlist não forem informados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/buscar/produto/{idProduto}/existe-na-wishlist/{idWishlist}")
    public VerificarProdutoExisteNaWishlistResponse verificaProdutosExisteNaWishlist(@Parameter(description = "Id do produto.") @PathVariable String idProduto,
                                                                                @Parameter(description = "Id da wishlist.") @PathVariable String idWishlist) {
        return verificarProdutoExisteNaWishlistInput.verificaProdutoExisteNaWishlist(idWishlist, idProduto);
    }


    @Operation(summary = "Adiciona uma nova wishlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist adicionada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdicionarNovaWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se id do cliente ou nome da wishlist não forem informados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se o cliente não existir.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @PostMapping
    public AdicionarNovaWishlistResponse adicionarNovaWishlist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para nova wishlist.", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdicionarNovaWishlistRequest.class)))
            @RequestBody AdicionarNovaWishlistRequest request){
        return adicionarNovaWishlistInput.adicionar(request.getNome(), request.getIdCliente());
    }


    @Operation(summary = "Busca uma wishlist por 'like' do seu nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultarWishlistResponseList.class))}),
            @ApiResponse(responseCode = "400", description = "Se nome da wishlist for null.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/buscar/por-nome/{nome}")
    public ConsultarWishlistResponseList consultaWishlistPorNome(@Parameter(description = "Nome da wishlist.") @PathVariable String nome) {
        return consultarWishlistPorNomeLikeInput.consulta(nome);
    }


    @Operation(summary = "Busca uma wishlist pela Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConsultarWishlistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Se a id da wishlist não for informada.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se a wishlist não for encontrada.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @GetMapping("/buscar/por-id/{id}")
    public ConsultarWishlistResponse consultaWishlistPorId(@Parameter(description = "Id da wishlist.") @PathVariable String id) {
        return consultarWishlistPorIdInput.consulta(id);
    }


    @Operation(summary = "Remove da wishlist um determinado produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remoção do produto realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Se a id da wishlist ou do produto não forem informadas.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Se o produto não for encontrado na wishlist, ou a wishlist não existe.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class))})
    })
    @DeleteMapping("/remover/da-wishlist/{idWishlist}/produto/{idProduto}")
    public void removerProdutoDaWishlist(@Parameter(description = "Id da wishlist.") @PathVariable String idWishlist,
                                         @Parameter(description = "Id do produto.") @PathVariable String idProduto){
        removerProdutoDaWishlistPorIdInput.removeDaWishlistPorId(idWishlist, idProduto);
    }
}
