package com.example.tiendaapi.services;

import com.example.tiendaapi.dtos.SessionCestaAddItemRequestDTO;
import com.example.tiendaapi.dtos.SessionCestaCheckoutRequestDTO;
import com.example.tiendaapi.dtos.SessionCestaCheckoutResponseDTO;
import com.example.tiendaapi.dtos.SessionCestaDTO;
import com.example.tiendaapi.dtos.SessionCestaItemDTO;
import com.example.tiendaapi.entities.Factura;
import com.example.tiendaapi.entities.Pedido;
import com.example.tiendaapi.entities.Producto;
import com.example.tiendaapi.entities.User;
import com.example.tiendaapi.repositories.FacturaRepository;
import com.example.tiendaapi.repositories.PedidoRepository;
import com.example.tiendaapi.repositories.ProductoRepository;
import com.example.tiendaapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionCestaService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    private final Map<String, Map<Integer, Integer>> cestasPorUsuario = new ConcurrentHashMap<>();

    public SessionCestaDTO addItem(String username, SessionCestaAddItemRequestDTO request) {
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto not found"));

        Map<Integer, Integer> cesta = cestasPorUsuario.computeIfAbsent(username, key -> new LinkedHashMap<>());
        int cantidadActual = cesta.getOrDefault(producto.getCodProducto(), 0);
        int nuevaCantidad = cantidadActual + request.getCantidad();

        if (producto.getStock() != null && nuevaCantidad > producto.getStock()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getDescripcion());
        }

        cesta.put(producto.getCodProducto(), nuevaCantidad);
        return buildSessionCestaDTO(username, cesta);
    }

    public SessionCestaDTO getCesta(String username) {
        Map<Integer, Integer> cesta = cestasPorUsuario.getOrDefault(username, new LinkedHashMap<>());
        return buildSessionCestaDTO(username, cesta);
    }

    public SessionCestaDTO removeItem(String username, Integer productoId) {
        Map<Integer, Integer> cesta = cestasPorUsuario.getOrDefault(username, new LinkedHashMap<>());
        cesta.remove(productoId);
        if (cesta.isEmpty()) {
            cestasPorUsuario.remove(username);
            return new SessionCestaDTO(username, new ArrayList<>(), 0, 0);
        }
        return buildSessionCestaDTO(username, cesta);
    }

    public void clearCesta(String username) {
        cestasPorUsuario.remove(username);
    }

    public SessionCestaCheckoutResponseDTO checkout(String username, SessionCestaCheckoutRequestDTO request) {
        SessionCestaDTO cestaActual = getCesta(username);
        if (cestaActual.getProductos().isEmpty()) {
            throw new RuntimeException("La cesta esta vacia");
        }

        System.out.println("=== Iniciando checkout ===");
        System.out.println("Productos en la cesta: " + cestaActual.getProductos().size());

        // Verificar stock de todos los productos antes de procesar
        List<Producto> productosToUpdate = new ArrayList<>();
        for (SessionCestaItemDTO item : cestaActual.getProductos()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));
            
            System.out.println("Producto: " + producto.getDescripcion());
            System.out.println("Stock actual: " + producto.getStock());
            System.out.println("Cantidad solicitada: " + item.getCantidad());
            
            if (producto.getStock() == null || producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getDescripcion() + 
                    " (Disponible: " + (producto.getStock() != null ? producto.getStock() : 0) + 
                    ", Solicitado: " + item.getCantidad() + ")");
            }
            
            // Disminuir stock
            producto.setStock(producto.getStock() - item.getCantidad());
            System.out.println("Nuevo stock: " + producto.getStock());
            productosToUpdate.add(producto);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUser(user);
        pedido.setImporte(cestaActual.getTotalPrecio());
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaPrevistaEntrega(request.getFechaPrevistaEntrega());
        pedido.setFechaEntrega(null);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        Factura factura = new Factura();
        factura.setPedido(pedidoGuardado);
        factura.setFechaFactura(LocalDate.now());
        factura.setFormaPago(request.getFormaPago());
        factura.setImporteTotal(cestaActual.getTotalPrecio());
        factura.setDescripcion(buildFacturaDescripcion(cestaActual));
        Factura facturaGuardada = facturaRepository.save(factura);

        // Guardar los cambios de stock
        System.out.println("Guardando cambios de stock en la BD...");
        List<Producto> productosGuardados = productoRepository.saveAll(productosToUpdate);
        System.out.println("Productos guardados: " + productosGuardados.size());
        for (Producto p : productosGuardados) {
            System.out.println("- " + p.getDescripcion() + ": Stock = " + p.getStock());
        }

        SessionCestaCheckoutResponseDTO response = new SessionCestaCheckoutResponseDTO(
                pedidoGuardado.getCodPedidos(),
                facturaGuardada.getCodFactura(),
                username,
                facturaGuardada.getFormaPago(),
                facturaGuardada.getFechaFactura(),
                cestaActual.getTotalItems(),
                cestaActual.getTotalPrecio(),
                cestaActual.getProductos()
        );

        System.out.println("=== Checkout completado ===");
        clearCesta(username);
        return response;
    }

    private SessionCestaDTO buildSessionCestaDTO(String username, Map<Integer, Integer> cesta) {
        List<SessionCestaItemDTO> productos = new ArrayList<>();
        int totalItems = 0;
        int totalPrecio = 0;

        for (Map.Entry<Integer, Integer> item : cesta.entrySet()) {
            Producto producto = productoRepository.findById(item.getKey())
                    .orElse(null);
            if (producto == null) {
                continue;
            }

            int cantidad = item.getValue();
            int precioUnitario = producto.getPrecioUnitario() == null ? 0 : producto.getPrecioUnitario();
            int subtotal = precioUnitario * cantidad;

            productos.add(new SessionCestaItemDTO(
                    producto.getCodProducto(),
                    producto.getDescripcion(),
                    precioUnitario,
                    cantidad,
                    subtotal
            ));

            totalItems += cantidad;
            totalPrecio += subtotal;
        }

        return new SessionCestaDTO(username, productos, totalItems, totalPrecio);
    }

    private String buildFacturaDescripcion(SessionCestaDTO cesta) {
        StringBuilder descripcion = new StringBuilder("Detalle factura:\n");
        for (SessionCestaItemDTO item : cesta.getProductos()) {
            descripcion.append("- ")
                    .append(item.getDescripcion())
                    .append(" x")
                    .append(item.getCantidad())
                    .append(" = ")
                    .append(item.getSubtotal())
                    .append("\n");
        }
        descripcion.append("Total items: ").append(cesta.getTotalItems()).append("\n");
        descripcion.append("Total precio: ").append(cesta.getTotalPrecio());
        return descripcion.toString();
    }
}
