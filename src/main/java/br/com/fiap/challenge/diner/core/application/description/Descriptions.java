package br.com.fiap.challenge.diner.core.application.description;

public class Descriptions {

    //Classe Error Response
    public static final String HTTP_CODE = "Código HTTP do erro";
    public static final String HTTP_DESCRIPTION = "Descrição do erro HTTP";
    public static final String MESSAGE = "Mensagem do erro";
    public static final String PATH = "Path da requisição";
    public static final String TIMESTAMP = "Horário do erro";
    public static final String FIELDS = "Lista de campos com erro";
    public static final String FIELD = "Nome do campo com erro";
    public static final String FIELD_MESSAGE = "Mensagem do erro";
    /*FIM*/

    public static final String RAZAO_SOCIAL = "Razão social";
    public static final String NOME_FANTANSIA = "Nome fantasia";
    public static final String CNPJ = "Cnpj";
    public static final String ID = "Identificador único";
    public static final String ATIVO = "Indicador ativo. Permitido (S=SIM/N=NAO)";
    public static final String PAGE = "Número da página";
    public static final String LIMIT = "Quantidade de registros para obter por página";
    public static final String SORT = "Ordenação da listagem";
    public static final String DESCRICAO_PRODUTO = "Descrição do produto";
    public static final String DESCRICAO_CATEGORIA = "Descrição da categoria";
    public static final String ID_PRODUTO = "Identificador único do produto";
    public static final String ID_CLIENTE = "Identificador único do cliente";
    public static final String ID_EMPRESA = "Identificador único da empresa";
    public static final String OBSERVACAO = "Campo livre para observações do pedido";
    public static final String NOME_CLIENTE = "Nome do cliente";
    public static final String CPF_CLIENTE = "CPF do cliente";
    public static final String TELEFONE_CLIENTE = "Número telefone";
    public static final String EMAIL_CLIENTE = "E-mail telefone";
    public static final String VLR_UNITARIO_PRODUTO = "Valor unitário do produto";
    public static final String CATEGORIA_PRODUTO = "Categoria associada ao produto";
    public static final String CATEGORIA_ID = "Identificar único da categoria";
    public static final String IMAGEM_PRODUTO = "Imagem para mostrar o produto";
    public static final String DESCRICAO_FORMA_PAGAMENTO = "Descrição forma de pagamento";
    public static final String TIPO_PAGAMENTO_FORMA_PAGAMENTO = "Tipo forma de pagamento. Valores permitidos [\"DEBITO\", \"CREDITO\", \"PIX\"]";
    public static final String LISTA_ITENS_PEDIDO = "Lista de itens do pedido";
    public static final String QTD_PRODUTO = "Quantidade de produtos";
    public static final String DATA_PEDIDO = "Data de quanto o pedido foi realizado. Formato: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String BLOCO_EMPRESA = "Bloco de informações da empresa";
    public static final String BLOCO_CLIENTE = "Bloco de informações do cliente";
    public static final String VLR_TOTAL = "Valor total do pedido";
    public static final String STATUS_PEDIDO = "Status do pedido. Disponíveis: RECEBIDO, EM_PREPARACAO, PRONTO, FINALIZADO";
    public static final String PEDIDO_ID = "Identificador único do pedido";
    public static final String FORMA_PAGAMENTO_ID = "Identificador único da forma de pagamento";
    public static final String QRCODE = "QRCode de pagamento";
    public static final String IDENTIFICADOR_PAGAMENTO = "Identificado unico de pagamento";
    public static final String VLR_PAGAMENTO = "Valor do pagamento feito";
    public static final String STATUS_PAGAMENTO = "Status do pagamento feito. Disponíveis: PENDENTE, CONFIRMADO, RECUSADO";

    // Paginação
    public static final String HAS_NEXT = "Indica se existe uma próxima página com elementos";
    public static final String HAS_PREVIOUS = "Indica se existe uma página anterior";
    public static final String PAGE_NUMBER = "Número da página atual";
    public static final String PAGE_SIZE = "Quantidade de elementos na página atual";
    public static final String ITEMS = "Lista de items da página";
}
