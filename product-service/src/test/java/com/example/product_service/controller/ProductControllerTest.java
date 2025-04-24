package com.example.product_service.controller;

import com.example.product_service.dto.ProductDTO;
import com.example.product_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductDTO sampleProduct = ProductDTO.builder()
            .id("123")
            .name("Test Product")
            .price(99.99)
            .build();

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(sampleProduct);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(99.99));
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById("123")).thenReturn(sampleProduct);

        mockMvc.perform(get("/products/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(sampleProduct));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value("123"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO updatedProduct = ProductDTO.builder()
                .id("123")
                .name("Updated Product")
                .price(150.0)
                .build();

        when(productService.updateProduct(eq("123"), any(ProductDTO.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct("123");

        mockMvc.perform(delete("/products/123"))
                .andExpect(status().isNoContent());
    }
}