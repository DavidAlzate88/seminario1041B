package co.edu.uniajc.controller;


import co.edu.uniajc.model.Order;
import co.edu.uniajc.model.Product;
import co.edu.uniajc.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Tag(name="Pedidos", description = "Operaciones relacionadas con pedidos")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido creado exitosamente",
                    content = {
                            @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos disponibles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos encontrada",
                    content = {
                            @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontraron pedidos", content = @Content),
            @ApiResponse(responseCode = "400", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve un pedido según su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}
