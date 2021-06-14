package br.com.luizalabs.wishlist.core.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AdicionarNovaWishlistRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String idCliente;

}
