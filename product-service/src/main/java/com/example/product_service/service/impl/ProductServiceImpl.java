package com.example.product_service.service.impl;

import com.example.product_service.dto.ProductDTO;
import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.debug("createProduct() - DTO recibido: {}", productDTO);
        Product product = mapper.toEntity(productDTO);
        Product saved = productRepository.save(product);
        ProductDTO result = mapper.toDTO(saved);
        log.info("createProduct() - Producto creado con ID={}", result.getId());
        return result;
    }

    @Override
    public ProductDTO getProductById(String id) {
        log.debug("getProductById() - Buscando producto con ID={}", id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("getProductById() - Producto no encontrado con ID={}", id);
                return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
            });
        ProductDTO dto = mapper.toDTO(product);
        log.info("getProductById() - Producto encontrado: {}", dto);
        return dto;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.debug("getAllProducts() - Recuperando todos los productos");
        List<ProductDTO> list = productRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        log.info("getAllProducts() - {} productos recuperados", list.size());
        return list;
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO dto) {
        log.debug("updateProduct() - Actualizando producto ID={} con datos: {}", id, dto);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("updateProduct() - Producto no encontrado con ID={}", id);
                return new ResourceNotFoundException("Producto no encontrado con ID: " + id);
            });

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        Product updated = productRepository.save(product);
        ProductDTO result = mapper.toDTO(updated);
        log.info("updateProduct() - Producto actualizado: {}", result);
        return result;
    }

    @Override
    public void deleteProduct(String id) {
        log.debug("deleteProduct() - Eliminando producto con ID={}", id);
        if (!productRepository.existsById(id)) {
            log.warn("deleteProduct() - No existe producto con ID={}", id);
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
        log.info("deleteProduct() - Producto con ID={} eliminado", id);
    }
}
