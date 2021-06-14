# Teste para Backend 
### Feature Wishlist - Luizalabs

**Definição**

O objetivo é que você desenvolva um serviço HTTP resolvendo a
funcionalidade de Wishlist do cliente. Esse serviço deve atender
os seguintes requisitos:
- **Adicionar** um produto na Wishlist do cliente;
- **Remover** um produto da Wishlist do cliente;
- **Consultar todos** os produtos da **Wishlist** do cliente;
- **Consultar** se um **determinado produto** está na **Wishlist** do
  cliente;

Imagine que esse serviço fará parte de uma plataforma
construída em uma arquitetura baseada em micro-serviços.
Portanto não se preocupe com a gestão das informações de
Produtos e/ou Clientes, coloque sua energia apenas no serviço da
Wishlist.

É importante estabelecer alguns limites para proteger o bom
funcionamento do ecossistema, dessa forma a Wishlist do cliente
deve possuir um **limite máximo de 20 produtos**


### Detalhamento técnico

A aplicação foi criada utilizando: 
- SpringBoot 
- MongoDB
- Java11
- Maven
- Docker

Foram criado quatro pacotes para organizar as classes, 
sendo o *core* e o *gateway* os principais.

**core** - foi criado para conter as classes da camada de negócio. 
O subpacote *usecase* contém todos os casos de uso requisitados.

**gateway** - criado para conter as camadas externas que se comunicam com a camada de negócio.
São dois subpacotes aqui *database* que contem as classes de persistencia, 
e o *rest* que contem as classes com os entrypoints do sistema. 

**Build e execução**

Para levantar o projeto é necessário executar o maven e o docker-compose.
O primeiro comando é build do maven:

`mvn clean package`

Após finalizar o build, deve ser gerado um arquivo *.jar* na pasta *target*.

O segundo comando é o docker-compose, para levar o MongoDB e a aplicação:

`docker-compose up --build`

Se tudo correr bem, o projeto está acessivel através do endereço:

[`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

Para encerrar a execução pode-se usar *ctrl+c* no termial que está executando, 
seguido do comando para remover os containers:

`docker-compose down`