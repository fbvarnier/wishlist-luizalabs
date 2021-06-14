package br.com.luizalabs.wishlist;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContainerBaseTest {
    protected final String URL_BASE = "http://localhost:8080";

    static MongoDBContainer mongoDBContainer;

    static {
        Map<String, String> env = new HashMap<>();
        env.put("MONGO_REPLICA_SET_NAME", "rs0");
        mongoDBContainer = new MongoDBContainer("mongo:latest")
                .withEnv(env);
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    public static void inicializaWishlist(MongoTemplate mongoTemplate){
        MongoCollection<Document> wishlistCollection = mongoTemplate.getCollection("wishlist");
        wishlistCollection.insertMany(Arrays.asList(
                Document.parse("{_id: \"1\",\n" +
                        "\"nome\": \"Lista Cheia\",\n" +
                        "\"idCliente\": \"1\",\n" +
                        "\"idProdutos\": [\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\",\"10\",\n" +
                        "\"11\",\"12\",\"13\",\"14\",\"15\",\"16\",\"17\",\"18\",\"19\",\"20\"]}"),
                Document.parse("{_id: \"2\",\n" +
                        "\"nome\": \"Lista Vazia\",\n" +
                        "\"idCliente\": \"1\",\n" +
                        "\"idProdutos\": []}")
                )
        );
    }
    public static void dropWishlist(MongoTemplate mongoTemplate){
        MongoCollection<Document> collection = mongoTemplate.getCollection("wishlist");
        collection.drop();
    }

    public static void inicializaProduto(MongoTemplate mongoTemplate){
        MongoCollection<Document> produtoCollection = mongoTemplate.getCollection("produto");
        produtoCollection.insertMany(Arrays.asList(
                Document.parse("{_id:\"1\"  ,\"nome\": \"Produto 1\" }"),
                Document.parse("{_id:\"2\"  ,\"nome\": \"Produto 2\" }"),
                Document.parse("{_id:\"3\"  ,\"nome\": \"Produto 3\" }"),
                Document.parse("{_id:\"4\"  ,\"nome\": \"Produto 4\" }"),
                Document.parse("{_id:\"5\"  ,\"nome\": \"Produto 5\" }"),
                Document.parse("{_id:\"6\"  ,\"nome\": \"Produto 6\" }"),
                Document.parse("{_id:\"7\"  ,\"nome\": \"Produto 7\" }"),
                Document.parse("{_id:\"8\"  ,\"nome\": \"Produto 8\" }"),
                Document.parse("{_id:\"9\"  ,\"nome\": \"Produto 9\" }"),
                Document.parse("{_id:\"10\" ,\"nome\": \"Produto 10\" }"),
                Document.parse("{_id:\"11\" ,\"nome\": \"Produto 11\" }"),
                Document.parse("{_id:\"12\" ,\"nome\": \"Produto 12\" }"),
                Document.parse("{_id:\"13\" ,\"nome\": \"Produto 13\" }"),
                Document.parse("{_id:\"14\" ,\"nome\": \"Produto 14\" }"),
                Document.parse("{_id:\"15\" ,\"nome\": \"Produto 15\" }"),
                Document.parse("{_id:\"16\" ,\"nome\": \"Produto 16\" }"),
                Document.parse("{_id:\"17\" ,\"nome\": \"Produto 17\" }"),
                Document.parse("{_id:\"18\" ,\"nome\": \"Produto 18\" }"),
                Document.parse("{_id:\"19\" ,\"nome\": \"Produto 19\" }"),
                Document.parse("{_id:\"20\" ,\"nome\": \"Produto 20\" }"),
                Document.parse("{_id:\"21\" ,\"nome\": \"Produto 21\" }"),
                Document.parse("{_id:\"22\" ,\"nome\": \"Produto 22\" }")
        ));
    }
    public static void dropProduto(MongoTemplate mongoTemplate){
        MongoCollection<Document> collection = mongoTemplate.getCollection("produto");
        collection.drop();
    }

    public static void inicializaCliente(MongoTemplate mongoTemplate){
        MongoCollection<Document> clienteCollection = mongoTemplate.getCollection("cliente");
        clienteCollection.insertMany(Arrays.asList(
                Document.parse("{_id:\"1\", \"nome\": \"Cliente 1\" }"),
                Document.parse("{_id:\"2\", \"nome\": \"Cliente 2\" }"),
                Document.parse("{_id:\"3\", \"nome\": \"Cliente 3\" }"),
                Document.parse("{_id:\"4\", \"nome\": \"Cliente 4\" }")
        ));
    }
    public static void dropCliente(MongoTemplate mongoTemplate){
        MongoCollection<Document> clienteCollection = mongoTemplate.getCollection("cliente");
        clienteCollection.drop();
    }


}
