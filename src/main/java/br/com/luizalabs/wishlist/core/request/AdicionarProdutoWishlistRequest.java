package br.com.luizalabs.wishlist.core.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AdicionarProdutoWishlistRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String idCliente;
    @NotBlank
    private String idProduto;

}
