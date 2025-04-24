package com.example.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private double price;
}
