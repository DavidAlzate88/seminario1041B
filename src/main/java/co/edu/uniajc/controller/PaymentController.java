package co.edu.uniajc.controller;

import co.edu.uniajc.model.Order;
import co.edu.uniajc.model.Payment;
import co.edu.uniajc.service.PaymentService;
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

@RestController
@RequestMapping("/payment")
@Tag(name="Pagos", description = "Operaciones relacionadas con pagos")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(
            summary = "Crear un pago relacionado un pedido ",
            description = "Crea un pago y lo guarda en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pago creado exitosamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos realizados", description = "Devuelve una lista de todos los pagos en bd")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pagos encontrada",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontraron pagos", content = @Content),
            @ApiResponse(responseCode = "400", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }
}
