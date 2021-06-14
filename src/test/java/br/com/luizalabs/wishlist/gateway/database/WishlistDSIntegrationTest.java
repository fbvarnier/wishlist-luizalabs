package br.com.luizalabs.wishlist.gateway.database;

import br.com.luizalabs.wishlist.AbstractContainerBaseTest;
import br.com.luizalabs.wishlist.Integration;
import br.com.luizalabs.wishlist.core.entity.Wishlist;
import br.com.luizalabs.wishlist.core.entity.factory.WishlistFactory;
import br.com.luizalabs.wishlist.core.entity.impl.WishlistImpl;
import br.com.luizalabs.wishlist.gateway.database.model.WishlistDataModel;
import net.jcip.annotations.NotThreadSafe;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@Testcontainers
@NotThreadSafe
@SpringBootTest
@Category(Integration.class)
class WishlistDSIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private WishlistDS wishlistDS;

    @Autowired
    private WishlistFactory wishlistFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void deveSalvar() {
        List<String> produtoList = new ArrayList<>();
        produtoList.add("produto");
        Wishlist wishlist = new WishlistImpl("lista", "cliente", produtoList);
        String idWishlist = "123456789";
        WishlistDataModel dataModel = this.wishlistDS.save(idWishlist, wishlist);

        Assertions.assertNotNull(dataModel);
    }
    @Test
    void buscarPorNome() {
        inicializaWishlist(mongoTemplate);
        List<WishlistDataModel> wishlistDataModelList = this.wishlistDS.buscarWishlistPorNomeLike("Lista Cheia");
        Assertions.assertEquals(1, wishlistDataModelList.size());
        dropWishlist(mongoTemplate);
    }


}
