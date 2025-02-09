INSERT INTO categoria(descricao)
VALUES ('Lanche'),
       ('Acompanhamento'),
       ('Bebida'),
       ('Sobremesa');

INSERT INTO empresa(razao_social, nome_fantasia, cnpj, ativo)
VALUES ('Razao Social 1', 'Nome Fantansia 1', '21833465000101', 'S'),
       ('Razao Social 2', 'Nome Fantansia 2', '80147108000140', 'S');

INSERT INTO forma_pagamento(descricao, tipo_pagamento)
VALUES ('QRCode - Mercado Pago', 'PIX');

INSERT INTO cliente(nome, cpf, telefone, email)
VALUES ('Cliente 1', '43478878683', null, null),
       ('Cliente 2', '43488151948', '34999999999', 'cliente2@gmail.com'),
       ('Cliente 3', '47184121619', '34888888888', 'cliente4@gmail.com');

INSERT INTO produto(descricao, vlr_unitario, categoria_id, imagem)
VALUES ('Produto 1', 1.51, 1, 'http://imagem.com.br/teste1.jpg'),
       ('Produto 2', 3.67, 2, 'http://imagem.com.br/teste2.jpg'),
       ('Produto 3', 7.97, 3, 'http://imagem.com.br/teste3.jpg'),
       ('Produto 4', 10.74, 4, 'http://imagem.com.br/teste4.jpg');