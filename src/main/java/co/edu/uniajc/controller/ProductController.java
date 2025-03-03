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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@Tag(name="Products resource")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Internal Server Error",
                    content = @Content)
    })
    public Product save(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos"),
            @ApiResponse(responseCode = "400", description = "Internal Server Error")
    })
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Internal Server Error")
    })
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
}
