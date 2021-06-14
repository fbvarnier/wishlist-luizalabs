package br.com.luizalabs.wishlist.core.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificarProdutoExisteNaWishlistResponse extends DefaultResponse {

    private boolean data = false;

    public VerificarProdutoExisteNaWishlistResponse(boolean data){
        this.data = data;
    }
}
