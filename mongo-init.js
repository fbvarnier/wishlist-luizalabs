print('Start #################################################################');

db = db.getSiblingDB('wishlist');

db.createCollection('produto');

db.produto.insertMany([
    {_id:"1"  , "nome": "Produto 1" },
    {_id:"2"  , "nome": "Produto 2" },
    {_id:"3"  , "nome": "Produto 3" },
    {_id:"4"  , "nome": "Produto 4" },
    {_id:"5"  , "nome": "Produto 5" },
    {_id:"6"  , "nome": "Produto 6" },
    {_id:"7"  , "nome": "Produto 7" },
    {_id:"8"  , "nome": "Produto 8" },
    {_id:"9"  , "nome": "Produto 9" },
    {_id:"10" , "nome": "Produto 10" },
    {_id:"11" , "nome": "Produto 11" },
    {_id:"12" , "nome": "Produto 12" },
    {_id:"13" , "nome": "Produto 13" },
    {_id:"14" , "nome": "Produto 14" },
    {_id:"15" , "nome": "Produto 15" },
    {_id:"16" , "nome": "Produto 16" },
    {_id:"17" , "nome": "Produto 17" },
    {_id:"18" , "nome": "Produto 18" },
    {_id:"19" , "nome": "Produto 19" },
    {_id:"20" , "nome": "Produto 20" },
    {_id:"21" , "nome": "Produto 21" },
    {_id:"22" , "nome": "Produto 22" }
]);

db.createCollection('cliente');

db.cliente.insertMany([
    {_id:"1", "nome": "Cliente 1" },
    {_id:"2", "nome": "Cliente 2" },
    {_id:"3", "nome": "Cliente 3" },
    {_id:"4", "nome": "Cliente 4" }
]);

db.createCollection('wishlist');

db.wishlist.insertMany([
    {     _id:"1",
          "nome": "Lista Cheia",
          "idCliente": "1",
          "idProdutos": ["1","2","3","4","5","6","7","8","9","10",
                         "11","12","13","14","15","16","17","18","19","20"
          ]
        },
        {
        _id:"2",
        "nome": "Lista Vazia",
        "idCliente": "1",
        "idProdutos": []}
]);


print('END #################################################################');