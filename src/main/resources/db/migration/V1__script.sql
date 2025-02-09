CREATE TABLE IF NOT EXISTS public.empresa (
id bigserial NOT NULL,
razao_social TEXT,
nome_fantasia TEXT,
cnpj TEXT,
ativo TEXT,
CONSTRAINT empresa_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN public.empresa.ativo IS 'S=SIM/N=NAO';

CREATE TABLE IF NOT EXISTS public.forma_pagamento (
id bigserial NOT NULL,
descricao TEXT,
tipo_pagamento TEXT,
CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN public.forma_pagamento.tipo_pagamento IS 'DEBITO/CREDITO/PIX';

CREATE TABLE IF NOT EXISTS public.cliente (
id bigserial NOT NULL,
nome TEXT NOT NULL,
cpf TEXT NOT NULL,
telefone TEXT,
email TEXT,
CONSTRAINT cliente_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_cliente_nome ON public.cliente USING btree (nome);

CREATE TABLE IF NOT EXISTS public.categoria (
id bigserial NOT NULL,
descricao TEXT NOT NULL,
CONSTRAINT categoria_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.produto (
id bigserial NOT NULL,
descricao TEXT NOT NULL,
vlr_unitario NUMERIC(15,2),
categoria_id bigint,
imagem TEXT NOT NULL,
CONSTRAINT produto_pkey PRIMARY KEY (id),
CONSTRAINT fk_produto_categoria_id FOREIGN KEY (categoria_id) REFERENCES public.categoria(id)
);

CREATE TABLE IF NOT EXISTS public.pedido (
id bigserial NOT NULL,
data timestamp NOT NULL,
empresa_id bigint NOT NULL,
cliente_id bigint,
vlr_total NUMERIC(15,2),
status TEXT NOT NULL DEFAULT 'R',
observacao TEXT,
CONSTRAINT pedido_pkey PRIMARY KEY (id),
CONSTRAINT fk_pedido_empresa_id FOREIGN KEY (empresa_id) REFERENCES public.empresa(id),
CONSTRAINT fk_pedido_cliente_id FOREIGN KEY (cliente_id) REFERENCES public.cliente(id)
);

COMMENT ON COLUMN public.pedido.status IS 'R=RECEBIDO/E=EM PREPARAÇÃO/P=PRONTO/F=FINALIZADO';

CREATE TABLE IF NOT EXISTS public.pedido_pagamento (
id bigserial NOT NULL,
forma_pagamento_id bigint NOT NULL,
pedido_id bigint NOT NULL,
vlr_pagamento NUMERIC(12,2) NOT NULL,
identificador_pagamento TEXT,
status TEXT NOT NULL DEFAULT 'P',
data_notificacao TEXT,
CONSTRAINT pedido_pagamento_pkey PRIMARY KEY (id),
CONSTRAINT fk_pedido_pagamento_forma_pagamento_id FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id),
CONSTRAINT fk_pedido_pagamento_pedido_id FOREIGN KEY (pedido_id) REFERENCES public.pedido(id)
);

COMMENT ON COLUMN public.pedido_pagamento.status IS 'P=PENDENTE/C=CONFIRMADO/R=RECUSADO';

CREATE TABLE IF NOT EXISTS public.pedido_item (
id bigserial NOT NULL,
produto_id bigint NOT NULL,
pedido_id bigint NOT NULL,
qtd_produto bigint,
vlr_unitario NUMERIC(12,2) NOT NULL,
CONSTRAINT pedido_item_pkey PRIMARY KEY (id),
CONSTRAINT fk_pedido_item_produto_id FOREIGN KEY (produto_id) REFERENCES public.produto(id),
CONSTRAINT fk_pedido_item_pedido_id FOREIGN KEY (pedido_id) REFERENCES public.pedido(id)
);