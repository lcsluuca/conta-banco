import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/padaria")
public class PadariaController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    // Endpoint para listar todos os produtos
    @GetMapping("/produtos")
    public Iterable<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Endpoint para criar um pedido com itens
    @PostMapping("/pedido")
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Endpoint para adicionar itens a um pedido existente
    @PostMapping("/pedido/{pedidoId}/adicionarItem")
    public Pedido adicionarItemAoPedido(@PathVariable Long pedidoId, @RequestBody ItemPedido itemPedido) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            itemPedido.setPedido(pedido);
            itemPedidoRepository.save(itemPedido);
            return pedidoRepository.save(pedido);
        } else {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
    }
}
