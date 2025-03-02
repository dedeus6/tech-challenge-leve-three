package br.com.fiap.app.usecases;

import br.com.fiap.webui.mappers.PedidoMapper;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.EmpresaResponse;
import br.com.fiap.webui.dtos.response.PaginacaoResponse;
import br.com.fiap.webui.dtos.response.PedidoResponse;
import br.com.fiap.app.repositories.ClienteRepository;
import br.com.fiap.app.repositories.EmpresaRepository;
import br.com.fiap.app.repositories.PedidoRepository;
import br.com.fiap.app.repositories.ProdutoRepository;
import br.com.fiap.utils.Paginacao;
import br.com.fiap.webui.dtos.ItemDTO;
import br.com.fiap.webui.dtos.PedidoDTO;
import br.com.fiap.app.entities.Cliente;
import br.com.fiap.app.entities.Empresa;
import br.com.fiap.app.entities.Pedido;
import br.com.fiap.app.entities.Produto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.errors.Errors.*;
import static br.com.fiap.utils.Numbers.isNonEmpty;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class PedidoUseCase {

    private static final Logger log = LoggerFactory.getLogger(PedidoUseCase.class);

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteRepository clienteRepository;

    private final PedidoMapper pedidoMapper;

    public PedidoResponse cadastrarPedido(PedidoDTO pedidoDTO) {
        var empresa = getEmpresa(pedidoDTO.getEmpresaId());
        var cliente = isNonEmpty(pedidoDTO.getClienteId()) ? getCliente(pedidoDTO.getClienteId()) : null;

        var pedido = pedidoMapper.toPedidoEntidade(pedidoDTO);
        pedido.setEmpresa(empresa);
        pedido.setCliente(cliente);

        getItensPedido(pedidoDTO.getItens(), pedido);
        pedido.calculaVlrTotal();

        var pedidoResponse = pedidoRepository.save(pedido);

        return pedidoMapper.toPedidoResponse(pedidoResponse);
    }

    public PedidoResponse buscarPedidoPorId(Long id) {
        var entity = getPedido(id);
        return pedidoMapper.toPedidoResponse(entity);
    }

    public PedidoResponse atualizaStatusPedido(Long id) {
        var pedido = getPedido(id);
        pedido.setStatus(pedido.getNextStatus());
        var pedidoResponse = pedidoRepository.save(pedido);
        return pedidoMapper.toPedidoResponse(pedidoResponse);
    }

    public PaginacaoResponse<PedidoResponse> listarPedidos(Integer page, Integer limit) {
        var pageable = Paginacao.getPageRequest(limit, page, "DESC", "id");
        var pedidos = pedidoRepository.findPedidosByCustomOrder(pageable);
        List<PedidoResponse> pedidosResponse = pedidoMapper.toResponseList(pedidos.getContent());
        return new PaginacaoResponse<List<EmpresaResponse>>()
                .convertToResponse(new PageImpl(pedidosResponse, pedidos.getPageable(), 0L));
    }

    private void getItensPedido(List<ItemDTO> itens, Pedido pedido) {
        itens.forEach(item -> {
            var produto = getProduto(item.getProdutoId());
            var itemPedido = pedido.addItem();
            itemPedido.setProduto(produto);
            itemPedido.setQtdProduto(item.getQtdProduto());
            itemPedido.setVlrUnitario(produto.getVlrUnitario());
        });
    }

    private Pedido getPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PEDIDO_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

    private Produto getProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PRODUTO_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

    private Empresa getEmpresa(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(EMPRESA_NAO_ENCONTRADA, UNPROCESSABLE_ENTITY));
    }

    private Cliente getCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CLIENTE_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

}
