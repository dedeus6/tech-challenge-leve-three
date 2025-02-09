# O problema

Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém o estabelecimento não estava preparado para sua expansão, com isso os pedidos sem um sistema que os gerencie se tornaram confusos e complicados, os funcionários começaram a perder os papéis que eram anotados os pedidos, a cozinha não tinha um direcionamento claro do que era cada pedido e os próprios antendentes não conseguiam lidar com a demanda total de clientes.

## Solução Proposta

A solução proposta ao estabelecimento é um sistema simples e de auto-atendimento para os clientes da lanchonete. Pensando em um grande sucesso da lanchonete, o sistema já foi pensado para suportar uma grande demanda de pedidos simultâneos e de forma totalmente escalável de acordo com a demanda do estabelecimento.

<div align="center">
  <img src="https://i.ibb.co/vXjQvJL/arquitetura.jpg" alt="Arquitetura Projeto">
</div>

## Tecnologias
- **Spring Boot**: Framework para construção de aplicações Java.
  - `spring-boot-starter-web`: Para construir aplicações web.
  - `spring-boot-starter-data-jpa`: Para integração com JPA (Java Persistence API).
  - `spring-boot-starter-validation`: Para validação de dados.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **MapStruct e ModelMapper**: Para mapeamento de objetos.
- **Flyway**: Para gerenciamento de migrações de banco de dados.
  - Estamos utilizando o Flyway para popular algumas tabelas de forma automática, otimizando o tempo para realização de testes e etc. Dentro das pastas /resources/db/migration nos arquivos de extensão .sql você encontra mais detalhes do que está sendo populado.
- **Spring Cloud OpenFeign**: Para facilitar chamadas de serviços REST.
- **Springdoc OpenAPI**: Para gerar documentação da API.
- **Kubernates**: Para orquestrar contêineres de maneira eficiente e automatizada
- **Minikube**: Execução de um cluster Kubernetes local em sua máquina

## Requisitos

- Java 21
- Docker e Docker Compose
- Minikube e Kubernetes configurados localmente

## Estrutura do Projeto
O projeto está dividido em dois contêineres: um para a aplicação Spring Boot e outro para o banco de dados PostgreSQL.

## Como Executar o Projeto

1. Iniciar o Minikube: Certifique-se de que o Minikube está instalado e configurado corretamente em sua máquina.

- Para iniciar o Minikube:
```
minikube start
```

2. Criando o Arquivo de Secret

- criar um arquivo de secret do tipo Opaque. Este tipo de secret é usado para armazenar dados arbitrários como senhas, tokens ou qualquer outro valor confidencial. O arquivo YAML para criar a secret deve ser algo como:

```
apiVersion: v1
kind: Secret
metadata:
  name: app-secrets
type: Opaque
data:
  MERCADO_PAGO_EXTERNAL_POS_ID: <senha-base64-encodificada-1>
  MERCADO_PAGO_SECRET_TOKEN: <senha-base64-encodificada-2>
  MERCADO_PAGO_USER_ID: <senha-base64-encodificada-3>
  SPRING_DATASOURCE_USERNAME: <senha-base64-encodificada-4>
  SPRING_DATASOURCE_PASSWORD: <senha-base64-encodificada-5>
```

### Explicação:

* **apiVersion**: Versão da API do Kubernetes para a criação da secret.
* **kind**: Tipo do recurso. Para secrets, deve ser Secret.
* **metadata**: Informações sobre a secret, como o nome.
* **type**: O tipo de secret. O Opaque é o tipo padrão e é usado para armazenar qualquer tipo de dados confidenciais codificados.
* **data**: A chave é o nome do dado (por exemplo, db-password), e o valor é a senha ou qualquer outra informação sensível, mas deve ser codificada em base64.

Para criar uma senha codificada em base64, você pode usar o comando base64 em um terminal:

```
echo -n 'minhasenha123' | base64
```

Isso gerará algo como:

```
bWluaGFzZW5oYTEyMw==
```

Substitua `<senha-base64-encodificada-1>` no arquivo YAML pela senha codificada. Assim, seu arquivo de secret ficaria assim:

```
apiVersion: v1
kind: Secret
metadata:
  name: app-secrets
type: Opaque
data:
  MERCADO_PAGO_EXTERNAL_POS_ID: bWluaGFzZW5oYTEyMw==
  MERCADO_PAGO_SECRET_TOKEN: <senha-base64-encodificada-2>
  MERCADO_PAGO_USER_ID: <senha-base64-encodificada-3>
  SPRING_DATASOURCE_USERNAME: <senha-base64-encodificada-4>
  SPRING_DATASOURCE_PASSWORD: <senha-base64-encodificada-5>
```

#### OBS: Todas as senhas que forem inseridas no arquivo app-secret.yml devem ser codificadas em base64.

3. Aplicar as Configurações do Kubernetes: Após iniciar o Minikube, aplique os arquivos de configuração do Kubernetes para implantar os serviços.

- Para aplicar as configurações de deploy da aplicação, banco de dados e serviços, execute os seguintes comandos:

```
kubectl apply -f app-deployment.yml
kubectl apply -f db-deployment.yml
kubectl apply -f app-service.yml
kubectl apply -f config-map.yml
kubectl apply -f db-service.yml
kubectl apply -f postgres-pvc.yml
kubectl apply -f app-secret.yml
kubectl apply -f hpa.yml
kubectl apply -f metrics.yml
```

4. Verificar o Status dos Pods e Serviços: Você pode verificar se os pods e serviços estão sendo executados corretamente usando os seguintes comandos:

- Verificar o status dos pods:

```
kubectl get pods
```

- Verificar o status dos serviços:

```
kubectl get svc
```

5. Acessar a API: Após aplicar as configurações e garantir que os pods estejam em execução, a API estará disponível para acesso.

- Para acessar a API, use o comando:

```
minikube service app-service --url
```

Isso fornecerá o endereço URL onde a API está sendo executada dentro do Minikube.

6. Documentação da API: A documentação da API pode ser acessada através do Swagger UI:

- Após a aplicação estar em execução, a documentação estará disponível em:

```
http://<url_service>/swagger-ui/index.html
```

## Considerações Finais

Este projeto utiliza o Minikube para rodar uma instância local do Kubernetes, que gerencia a implantação dos serviços de backend e banco de dados. Certifique-se de ter o Minikube e o kubectl configurados corretamente para evitar problemas na execução.
