# Projeto SpringIonic Backend - Plataforma de e-commerce

## Descrição
O SpringIonic é uma aplicação desenvolvida com Spring Boot e Ionic que oferece uma plataforma completa para a criação de lojas virtuais. A aplicação permite o cadastro de produtos, categorias, clientes e pedidos, além de oferecer funcionalidades como busca de produtos, gerenciamento de carrinho de compras e pagamento.

## Tecnologias
* **Back-end:** Spring Boot, Java, Spring Data JPA, Hibernate, MySQL, Amazon S3 (opcional)
* **Front-end:** Ionic Framework
* **Outras:** JWT para autenticação, BCrypt para criptografia de senhas, Jackson para serialização/desserialização de objetos JSON.

## Funcionalidades
* **Cadastro e gerenciamento de usuários:** Permite o cadastro de clientes com diferentes perfis, atualização de dados pessoais e histórico de pedidos.
* **Cadastro e gerenciamento de produtos:** Permite o cadastro de produtos com suas respectivas categorias, informações detalhadas e imagens.
* **Gerenciamento de categorias:** Permite a criação e organização de produtos em categorias.
* **Carrinho de compras:** Permite aos usuários adicionar produtos ao carrinho, visualizar o resumo da compra e realizar o pagamento.
* **Pagamento:** Suporta diferentes formas de pagamento, como boleto bancário e cartão de crédito.
* **Pedidos:** Permite a realização de pedidos, acompanhamento do status do pedido e histórico de pedidos.
* **Autenticação:** Utiliza JWT para garantir a segurança das requisições.
* **Autorização:** Controle de acesso aos recursos da API baseado em roles de usuário (ADMIN e CLIENT).

## Recursos da API

| Endpoint | Descrição | Método | Acesso |
|---|---|---|---|
| `/auth/login` | Realiza o login na aplicação | `POST` | Acesso público |
| `/auth/refresh_token` | Gera um novo token de acesso | `POST` | Autenticado |
| `/auth/forgot` | Solicita uma nova senha | `POST` | Acesso público |
| `/categories` | Retorna lista de categorias | `GET` | Acesso público |
| `/categories/{id}` | Retorna uma categoria por id | `GET` | Acesso público |
| `/categories/page` | Retorna lista de categorias paginadas | `GET` | Acesso público |
| `/categories` | Insere uma nova categoria | `POST` | Role 'ADMIN' |
| `/categories/{id}` | Atualiza uma categoria | `PUT` | Role 'ADMIN' |
| `/categories/{id}` | Deleta uma categoria | `DELETE` | Role 'ADMIN' |
| `/clients` | Retorna lista de clientes | `GET` | Role 'ADMIN' |
| `/clients/{id}` | Retorna um cliente por id | `GET` | Acesso público |
| `/clients/email` | Retorna um cliente por email | `GET` | Acesso público |
| `/clients/page` | Retorna lista de clientes paginadas | `GET` | Role 'ADMIN' |
| `/clients` | Insere um novo cliente | `POST` | Acesso público |
| `/clients/{id}` | Atualiza um cliente | `PUT` | Acesso público |
| `/clients/{id}` | Deleta um cliente | `DELETE` | Role 'ADMIN' |
| `/clients/picture` | Realiza upload da foto de perfil do cliente | `POST` | Autenticado |
| `/orders` | Retorna lista de pedidos | `GET` | Autenticado |
| `/orders/{id}` | Retorna um pedido por id | `GET` | Autenticado |
| `/orders` | Insere um novo pedido | `POST` | Autenticado |
| `/orders` | Retorna lista de pedidos paginadas | `GET` | Autenticado |
| `/products` | Retorna lista de produtos | `GET` | Acesso público |
| `/products/{id}` | Retorna um produto por id | `GET` | Acesso público |
| `/products` | Retorna lista de produtos paginadas | `GET` | Acesso público |
| `/states` | Retorna lista de estados | `GET` | Acesso público |
| `/states/{stateId}/cities` | Retorna lista de cidades de um determinado estado | `GET` | Acesso público |

### Detalhando o Endpoint `/auth/login`

#### Descrição
O endpoint `/auth/login` é responsável por autenticar um usuário no sistema. Ao receber as credenciais (email e senha) do usuário em uma requisição POST, ele verifica se as informações são válidas e, caso sejam, retorna um token JWT (JSON Web Token) para o cliente. Esse token será utilizado em todas as requisições subsequentes para identificar o usuário autenticado.

#### Método HTTP
* **POST:** O método HTTP POST é utilizado para enviar as credenciais do usuário para o servidor.

#### URL
* `/auth/login`

#### Parâmetros de Requisição
* **Corpo da requisição:**
  * **email:** O endereço de email do usuário (string).
  * **password:** A senha do usuário (string).

#### Resposta
* **Sucesso (200 OK):** Retorna um objeto JSON com os seguintes campos:
  * **email:** O email do usuário.
  * **token:** O token JWT gerado para o usuário.
* **Falha (401 Unauthorized):** Retorna um código de status 401 Unauthorized se as credenciais forem inválidas.

#### Exemplo de requisição
```
POST /auth/login
Content-Type: application/json
```

```json
{
    "email": "usuario@example.com",
    "password": "minhaSenha123"
}
```

#### Exemplo de resposta (sucesso)
```json
{
    "email": "usuario@example.com",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTYiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

#### Exemplo de resposta (falha)
```
HTTP/1.1 401 Unauthorized
```

#### Implementação (Exemplo)
```java
@PostMapping("/login")
public ResponseEntity<CredentialsDTO> login(@RequestBody CredentialsDTO body) {
    Client client = repository.findByEmail(body.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado"));

    if (passwordEncoder.matches(body.getPassword(), client.getPassword())) {
        String token = tokenService.generateToken(client);
        return ResponseEntity.ok(new CredentialsDTO(client.getEmail(), token));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
```

### Detalhando o Endpoint `/categories`

#### Descrição
O endpoint `/categories` é responsável por gerenciar as categorias de produtos na aplicação. Ele permite listar todas as categorias existentes, criar novas categorias e deletar categorias existentes.

#### Métodos HTTP
* **GET:** Retorna uma lista de todas as categorias cadastradas no sistema.
* **POST:** Cria uma nova categoria.

#### URL
* `/categories`

#### Parâmetros de Requisição
* **GET:** Não possui parâmetros.
* **POST:**
  * **Corpo da requisição:**
    * **name:** O nome da nova categoria (string).

#### Resposta
* **GET:**
  * **Sucesso (200 OK):** Retorna uma lista de objetos JSON, onde cada objeto representa uma categoria e contém os seguintes campos:
    * **id:** O identificador único da categoria.
    * **name:** O nome da categoria.
* **POST:**
  * **Sucesso (201 Created):** Retorna um código de status 201 Created e a localização da nova categoria no cabeçalho `Location`.
  * **Falha (400 Bad Request):** Retorna um código de status 400 Bad Request se a requisição estiver inválida (por exemplo, se o campo "name" estiver vazio).

#### Exemplo de requisição (GET)
```
GET /categories
```

#### Exemplo de resposta (GET)
```json
[
    {
        "id": 1,
        "name": "Eletrônicos"
    },
    {
        "id": 2,
        "name": "Roupas"
    }
]
```

#### Exemplo de requisição (POST)
```
POST /categories
Content-Type: application/json
```

```json
{
    "name": "Livros"
}
```

#### Implementação (Exemplo)
```java
@GetMapping
public ResponseEntity<List<CategoryDTO>> findAll() {
    List<CategoryDTO> list = service.findAll();
    return ResponseEntity.ok().body(list);
}

@PostMapping
public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).build();
}
```

### Detalhando o Endpoint `/clients`

#### Descrição

O endpoint `/clients` é responsável por gerenciar os clientes da aplicação. Ele permite listar todos os clientes cadastrados, criar novos clientes, atualizar informações de clientes existentes e deletar clientes.

#### Métodos HTTP

* **GET:** Retorna uma lista de todos os clientes cadastrados no sistema.
* **POST:** Cria um novo cliente.
* **PUT:** Atualiza um cliente existente.
* **DELETE:** Deleta um cliente existente.

#### URL

* `/clients`

#### Parâmetros de Requisição

* **GET:** Não possui parâmetros.
* **POST:**
    * **Corpo da requisição:**
        * **name:** O nome completo do cliente (string).
        * **email:** O endereço de email do cliente (string).
        * **password:** A senha do cliente (string).
        * **other fields:** Outros campos como telefone, endereço, etc. (dependendo da sua aplicação).
* **PUT:**
    * **Corpo da requisição:**
        * **id:** O identificador único do cliente.
        * **name:** O novo nome do cliente (opcional).
        * **email:** O novo endereço de email do cliente (opcional).
        * **password:** A nova senha do cliente (opcional).
        * **other fields:** Outros campos a serem atualizados (opcional).
* **DELETE:**
    * **Parâmetro de caminho:**
        * **id:** O identificador único do cliente a ser deletado.

#### Resposta

* **GET:**
    * **Sucesso (200 OK):** Retorna uma lista de objetos JSON, onde cada objeto representa um cliente e contém os campos relevantes (id, nome, email, etc.).
* **POST:**
    * **Sucesso (201 Created):** Retorna um código de status 201 Created e a localização do novo cliente no cabeçalho `Location`.
    * **Falha (400 Bad Request):** Retorna um código de status 400 Bad Request se a requisição estiver inválida (por exemplo, se o campo "email" já existir).
* **PUT:**
    * **Sucesso (204 No Content):** Retorna um código de status 204 No Content se a atualização for bem-sucedida.
    * **Falha (404 Not Found):** Retorna um código de status 404 Not Found se o cliente não for encontrado.
* **DELETE:**
    * **Sucesso (204 No Content):** Retorna um código de status 204 No Content se a deleção for bem-sucedida.
    * **Falha (404 Not Found):** Retorna um código de status 404 Not Found se o cliente não for encontrado.

#### Exemplos de requisição

**POST**
```
POST /clients
Content-Type: application/json
```

```json
{
    "name": "João da Silva",
    "email": "joao@example.com",
    "password": "minhaSenha123"
}
```

**PUT**
```json
PUT /clients/1
Content-Type: application/json
```

```json
{
    "name": "João Silva Santos"
}
```

**DELETE**
```
DELETE /clients/1
```

#### Implementação (Exemplo)
```java
@PostMapping
public ResponseEntity<Void> insert(@Valid @RequestBody ClientDTO dto) {
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(dto.getId()).toUri();
    return ResponseEntity.created(uri).build();
}

@PutMapping("/{id}")
public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO dto, @PathVariable Long id) {
    dto.setId(id);
    service.update(dto);
    return ResponseEntity.noContent().build();
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
}
```

### Detalhando o Endpoint `/orders`

#### Descrição

O endpoint `/orders` é responsável por gerenciar os pedidos dos clientes. Ele permite listar todos os pedidos de um cliente, criar novos pedidos, atualizar o status de um pedido e obter detalhes de um pedido específico.

#### Métodos HTTP

* **GET:** Retorna uma lista de todos os pedidos de um cliente ou todos os pedidos do sistema (dependendo da implementação).
* **POST:** Cria um novo pedido.
* **PUT:** Atualiza o status de um pedido.

#### URL

* `/orders`

#### Parâmetros de Requisição

* **GET:**
    * **Parâmetro de consulta:**
        * `clientId`: O identificador do cliente (opcional, se você quiser listar todos os pedidos).
* **POST:**
    * **Corpo da requisição:**
        * `clientId`: O identificador do cliente.
        * `items`: Uma lista de itens do pedido, cada item contendo o id do produto, a quantidade e o preço unitário.
        * `address`: O endereço de entrega.
        * `payment`: As informações de pagamento.
* **PUT:**
    * **Parâmetro de caminho:**
        * `id`: O identificador único do pedido.
    * **Corpo da requisição:**
        * `status`: O novo status do pedido (por exemplo, "pending", "shipped", "delivered").

#### Resposta

* **GET:**
    * **Sucesso (200 OK):** Retorna uma lista de objetos JSON, onde cada objeto representa um pedido e contém os campos relevantes (id, cliente, itens, status, data).
* **POST:**
    * **Sucesso (201 Created):** Retorna um código de status 201 Created e a localização do novo pedido no cabeçalho `Location`.
    * **Falha (400 Bad Request):** Retorna um código de status 400 Bad Request se a requisição estiver inválida (por exemplo, se o cliente não existir).
* **PUT:**
    * **Sucesso (204 No Content):** Retorna um código de status 204 No Content se a atualização for bem-sucedida.
    * **Falha (404 Not Found):** Retorna um código de status 404 Not Found se o pedido não for encontrado.

#### Exemplos de requisição

**POST**
```
POST /orders
Content-Type: application/json
```

```json
{
    "clientId": 1,
    "items": [
        {
            "productId": 10,
            "quantity": 2,
            "unitPrice": 19.99
        }
    ],
    "address": {
        "street": "Rua Principal",
        "number": 123,
        "city": "São Paulo",
        "state": "SP",
        "zip": "01234-567"
    },
    "payment": {
        "method": "creditCard",
        "cardNumber": "1234567890123456"
    }
}
```

**PUT**
```
PUT /orders/1
Content-Type: application/json
```

```json
{
    "status": "shipped"
}
```

#### Implementação (Exemplo)
```java
@PostMapping
public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
    Order order = orderService.createOrder(orderDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(order.getId()).toUri();
    return ResponseEntity.created(uri).body(order);
}

@PutMapping("/{id}")
public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusDTO statusDTO) {
    orderService.updateOrderStatus(id, statusDTO.getStatus());
    return ResponseEntity.noContent().build();
}
```

### Detalhando o Endpoint `/products`

#### Descrição

O endpoint `/products` é responsável por gerenciar os produtos da loja virtual. Ele permite listar todos os produtos, criar novos produtos, atualizar informações de produtos existentes e deletar produtos.

#### Métodos HTTP

* **GET:** Retorna uma lista de todos os produtos cadastrados no sistema, podendo ser filtrada por categoria ou outros critérios.
* **POST:** Cria um novo produto.
* **PUT:** Atualiza um produto existente.
* **DELETE:** Deleta um produto existente.

#### URL

* `/products`

#### Parâmetros de Requisição

* **GET:**
    * **Parâmetros de consulta:**
        * `categoryId`: Filtra os produtos por categoria.
        * `name`: Busca produtos por nome (contendo o termo).
* **POST:**
    * **Corpo da requisição:**
        * `name`: O nome do produto.
        * `description`: A descrição do produto.
        * `price`: O preço do produto.
        * `categoryId`: O identificador da categoria do produto.
        * `imageUrl`: A URL da imagem do produto.
        * `stockQuantity`: A quantidade em estoque.
* **PUT:**
    * **Parâmetro de caminho:**
        * `id`: O identificador único do produto.
    * **Corpo da requisição:**
        * Quaisquer campos que você deseja atualizar (nome, descrição, preço, etc.).
* **DELETE:**
    * **Parâmetro de caminho:**
        * `id`: O identificador único do produto a ser deletado.

#### Resposta

* **GET:**
    * **Sucesso (200 OK):** Retorna uma lista de objetos JSON, onde cada objeto representa um produto e contém os campos relevantes (id, nome, descrição, preço, categoria, imagem, estoque).
* **POST:**
    * **Sucesso (201 Created):** Retorna um código de status 201 Created e a localização do novo produto no cabeçalho `Location`.
    * **Falha (400 Bad Request):** Retorna um código de status 400 Bad Request se a requisição estiver inválida (por exemplo, se o campo "name" estiver vazio).
* **PUT:**
    * **Sucesso (204 No Content):** Retorna um código de status 204 No Content se a atualização for bem-sucedida.
    * **Falha (404 Not Found):** Retorna um código de status 404 Not Found se o produto não for encontrado.
* **DELETE:**
    * **Sucesso (204 No Content):** Retorna um código de status 204 No Content se a deleção for bem-sucedida.
    * **Falha (404 Not Found):** Retorna um código de status 404 Not Found se o produto não for encontrado.

#### Exemplos de requisição

**GET (com filtro por categoria)**
```
GET /products?categoryId=1
```

**POST**
```
POST /products
Content-Type: application/json
```

```json
{
    "name": "Smartphone X",
    "description": "Smartphone de última geração",
    "price": 2999.99,
    "categoryId": 1,
    "imageUrl": "https://example.com/smartphone.jpg",
    "stockQuantity": 100
}
```

**PUT**
```
PUT /products/1
Content-Type: application/json
```

```json
{
    "price": 2799.99
}
```

#### Implementação (Exemplo)
```java
@GetMapping
public ResponseEntity<List<ProductDTO>> findAll(@RequestParam(required = false) Long categoryId) {
    List<ProductDTO> list = service.findAll(categoryId);
    return ResponseEntity.ok().body(list);
}

@PostMapping
public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
    Product product = service.createProduct(productDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(product.getId()).toUri();
    return ResponseEntity.created(uri).body(product);
}
```

### Detalhando o Endpoint `/states`

#### Descrição

O endpoint `/states` é responsável por gerenciar os estados de um país. Ele permite listar todos os estados cadastrados, criar novos estados e buscar estados por nome. 

#### Métodos HTTP

* **GET:** Retorna uma lista de todos os estados cadastrados no sistema, podendo ser filtrada por nome.
* **POST:** Cria um novo estado.

#### URL

* `/states`

#### Parâmetros de Requisição

* **GET:**
    * **Parâmetros de consulta:**
        * `name`: Busca estados por nome (contendo o termo).
* **POST:**
    * **Corpo da requisição:**
        * `name`: O nome do estado.
        * `acronym`: A sigla do estado.
        * `countryId`: O identificador do país ao qual o estado pertence.

#### Resposta

* **GET:**
    * **Sucesso (200 OK):** Retorna uma lista de objetos JSON, onde cada objeto representa um estado e contém os campos relevantes (id, nome, sigla, país).
* **POST:**
    * **Sucesso (201 Created):** Retorna um código de status 201 Created e a localização do novo estado no cabeçalho `Location`.
    * **Falha (400 Bad Request):** Retorna um código de status 400 Bad Request se a requisição estiver inválida (por exemplo, se o campo "name" estiver vazio).

#### Exemplos de requisição

**GET (com filtro por nome)**
```
GET /states?name=São%20Paulo
```

**POST**
```
POST /states
Content-Type: application/json
```

```json
{
    "name": "Rio de Janeiro",
    "acronym": "RJ",
    "countryId": 1
}
```

#### Implementação (Exemplo)
```java
@GetMapping
public ResponseEntity<List<StateDTO>> findAll(@RequestParam(required = false) String name) {
    List<StateDTO> list = service.findAll(name);
    return ResponseEntity.ok().body(list);
}

@PostMapping
public ResponseEntity<State> createState(@Valid @RequestBody StateDTO stateDTO) {
    State state = service.createState(stateDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(state.getId()).toUri();
    return ResponseEntity.created(uri).body(state);
}
```

## Diagrama de Arquitetura Simplificado

```mermaid
graph LR
  subgraph Banco de Dados
    Cliente((Cliente))
    Estado((Estado))
    Cidade((Cidade))
    Categoria((Categoria))
    Pagamento((Pagamento)) || {abstract}
      Pagamento_Boleto((Pagamento Boleto)) --> Pagamento
      Pagamento_Cartao((Pagamento Cartao)) --> Pagamento
    Pedido((Pedido))
    Item_Pedido((Item Pedido))
    Produto((Produto))
  end
  Cliente --> Endereço{1..N}
  Cliente --> Telefone{1..N}
  Pedido --> Cliente
  Pedido --> Pagamento
  Pedido --> Item_Pedido{1..N}
  Item_Pedido --> Produto
  Produto --> Categoria{1..N}
  Cidade --> Estado
```


## Instalação e Execução

**Instalação**
1. **Pré-requisitos:**
   * **Java:** Instale o JDK (Java Development Kit) da versão 8 ou superior.
   * **Maven:** Instale o Apache Maven para gerenciamento de dependências.
   * **Banco de dados:** Configure um banco de dados MySQL.
   * **Ferramenta de linha de comando:** Git para clonar o repositório.
2. **Clonar o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/springionic.git
   ```
3. **Configurar o arquivo de propriedades:**
   * Edite o arquivo `application.properties` e configure as informações de conexão com o banco de dados.
4. **Construir o projeto:**
   ```bash
   cd springionic
   mvn clean install
   ```
5. **Executar a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

**Execução**
A aplicação estará acessível na porta 8080. Por exemplo, para acessar a lista de produtos, utilize a URL `http://localhost:8080/products`.

## Contribuição

Agradecemos sua contribuição para o projeto SpringIonic! Para contribuir, siga estes passos:
1. **Fork** o repositório.
2. **Clone** o seu fork para sua máquina local.
3. **Crie um novo branch** para sua feature ou correção de bug.
4. **Faça suas alterações** e commit.
5. **Envie um pull request** para o repositório original.

## Licença
Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT). Consulte o arquivo LICENSE para mais detalhes.
