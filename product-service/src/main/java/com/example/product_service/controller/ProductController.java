package com.example.product_service.controller;

import com.example.product_service.dto.ProductDTO;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Productos", description = "Operaciones CRUD para productos")
public class ProductController {

    private final ProductService productService;

    @Operation(
        summary = "Crear un producto",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
                         content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto) {
        log.info("POST /products - Crear producto: {}", dto);
        ProductDTO createdProduct = productService.createProduct(dto);
        log.info("Producto creado con ID: {}", createdProduct.getId());
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(
        summary = "Obtener un producto por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                         content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id) {
        log.info("GET /products/{} - Obtener producto por ID", id);
        ProductDTO product = productService.getProductById(id);
        log.debug("Producto encontrado: {}", product);
        return ResponseEntity.ok(product);
    }

    @Operation(
        summary = "Obtener todos los productos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Listado de productos",
                         content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        log.info("GET /products - Obtener todos los productos");
        List<ProductDTO> products = productService.getAllProducts();
        log.debug("Número de productos encontrados: {}", products.size());
        return ResponseEntity.ok(products);
    }

    @Operation(
        summary = "Actualizar un producto existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                         content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @Valid @RequestBody ProductDTO dto) {
        log.info("PUT /products/{} - Actualizar producto con ID: {}", id, dto);
        ProductDTO updatedProduct = productService.updateProduct(id, dto);
        log.info("Producto actualizado con ID: {}", updatedProduct.getId());
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(
        summary = "Eliminar un producto",
        responses = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("DELETE /products/{} - Eliminar producto con ID: {}", id);
        productService.deleteProduct(id);
        log.info("Producto con ID {} eliminado", id);
        return ResponseEntity.noContent().build();
    }
}