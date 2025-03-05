package br.com.fiap.errors;

public class Errors {

    //GENERICOS
    public static final String CAMPO_REQUERIDO = "Campo é requerido";
    public static final String QUERY_PARAMS_REQUERIDO = "Parâmetro é requerido";
    public static final String VALOR_MAIOR_QUE_0 = "O valor deve ser maior que zero";

    // Empresa
    public static final String CNPJ_INVALIDO = "Cnpj informado é diferente de 14 carácteres";
    public static final String EMPRESA_JA_CADASTRADA = "Empresa já cadastrada";
    public static final String EMPRESA_NAO_ENCONTRADA = "Empresa não encontrada";

    // Produto
    public static final String VLR_UNITARIO_FORMATO_INVALIDO = "O valor unitário deve ter no máximo 10 dígitos inteiros e 2 casas decimais";
    public static final String CATEGORIA_NAO_EXISTE = "A categoria informada não existe";
    public static final String CLIENTE_EXISTE = "O CPF informado já existe cadastrado";
    public static final String CLIENTE_NAO_EXISTE = "Cliente não encontrado";
    public static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
    public static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado";
    public static final String FORMA_PAGAMENTO_NAO_ENCONTRADO = "Forma de pagamento não encontrada";

    // Pedido
    public static final String PEDIDO_VALOR_TOTAL_ZERO = "Pedido possuí valor para pagamento zerado";
    public static final String FORMA_PAGAMENTO_NAO_DISPONIVEL = "Forma de pagamento não está disponível";
    public static final String PEDIDO_STATUS_DIFERENTE_RECEBIDO = "Status do pedido é diferente de recebido";
    public static final String VALOR_TOTAL_PEDIDO_DIFERENTE_SOMATORIO_ITENS = "Valor total do pedido é diferente do somatório dos itens";
    public static final String LISTA_ITENS_DEVE_TER_UM_ELEMENTO = "A lista de itens deve conter pelo menos um elemento";
    public static final String STATUS_INVALIDO = "O pedido deve estar em preparação para ir para pronto ou pronto para ir para finalizado.";
    public static final String PEDIDO_PAGAMENTO_INVALIDO = "Pagamento não encontrado.";

    // Paginação
    public static final String PAGE_MINIMA = "Página miníma é 1";

    // Cliente
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";

    //ENUMS
    public static final String ENUM_ATIVO_INVALIDO = "O valor informado está invalido. Valores permitidos [S, N]";

}
