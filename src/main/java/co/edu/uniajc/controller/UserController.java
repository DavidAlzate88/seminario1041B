package co.edu.uniajc.controller;

import co.edu.uniajc.model.Product;
import co.edu.uniajc.model.User;
import co.edu.uniajc.service.ProductService;
import co.edu.uniajc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name="User resource")
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Obtener usuario por email", description = "Devuelve un usuario segun su email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Internal Server Error")
    })
    public User getUserByEmail(@RequestBody String email) {
        return userService.findByEmail(email);
    }
}
