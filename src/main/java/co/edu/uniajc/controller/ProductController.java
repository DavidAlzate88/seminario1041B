package co.edu.uniajc.controller;

import co.edu.uniajc.model.Product;
import co.edu.uniajc.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@Tag(name="Productos", description = "Operaciones relacionadas con productos")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado exitosamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Product> save(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.createProduct(product));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<List<Product>> getProducts() {
        try {
            return ResponseEntity.ok(productService.findAll());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto según su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty());
        }
    }
}