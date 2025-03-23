# O problema 

Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém o estabelecimento não estava preparado para sua expansão, com isso os pedidos sem um sistema que os gerencie se tornaram confusos e complicados, os funcionários começaram a perder os papéis que eram anotados os pedidos, a cozinha não tinha um direcionamento claro do que era cada pedido e os próprios antendentes não conseguiam lidar com a demanda total de clientes.

## Solução Proposta

A solução proposta ao estabelecimento é um sistema simples e de auto-atendimento para os clientes da lanchonete. Pensando em um grande sucesso da lanchonete, o sistema já foi pensado para suportar uma grande demanda de pedidos simultâneos e de forma totalmente escalável de acordo com a demanda do estabelecimento.

<div align="center">
  <img src="https://i.ibb.co/vXjQvJL/arquitetura.jpg" alt="Arquitetura Projeto">
</div>

## Modelo de Entidade e Relacionamento

<div align="center">
  <img src="https://i.ibb.co/BHGcjhpk/DB.png" alt="Modelo de Entidade e Relacionamento do Banco de Dados PostgreSQL">
</div>

## Tecnologias
- **Spring Boot**: Framework para construção de aplicações Java.
  - `spring-boot-starter-web`: Para construir aplicações web.
  - `spring-boot-starter-data-jpa`: Para integração com JPA (Java Persistence API).
  - `spring-boot-starter-validation`: Para validação de dados.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional, selecionado pois entendemos que a estrutura dos dados foi bem definida em outra fase e por isso não precisamos de toda flexibilidade de um banco NoSQL. 
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **MapStruct e ModelMapper**: Para mapeamento de objetos.
- **Flyway**: Para gerenciamento de migrações de banco de dados.
  - Estamos utilizando o Flyway para popular algumas tabelas de forma automática, otimizando o tempo para realização de testes e etc. Dentro das pastas /resources/db/migration nos arquivos de extensão .sql você encontra mais detalhes do que está sendo populado.
- **Spring Cloud OpenFeign**: Para facilitar chamadas de serviços REST.
- **Springdoc OpenAPI**: Para gerar documentação da API.
- **Kubernates**: Para orquestrar contêineres de maneira eficiente e automatizada
- **Terraform**: Para gerenciamento do nosso IaC
- **AWS**: Toda a nossa infraestrutura em nuvem.

## Requisitos

- Java 21
- Docker e Docker Compose
- Minikube e Kubernetes configurados localmente

## Estrutura do Projeto
O projeto está dividido em dois contêineres: um para a aplicação Spring Boot e outro para o banco de dados PostgreSQL.

## Documentação da API

- Após a aplicação estar em execução, a documentação estará disponível em:

```
http://<url_service>/swagger-ui/index.html
```

## Considerações Finais

Este projeto utiliza o Minikube para rodar uma instância local do Kubernetes, que gerencia a implantação dos serviços de backend e banco de dados. Certifique-se de ter o Minikube e o kubectl configurados corretamente para evitar problemas na execução.
