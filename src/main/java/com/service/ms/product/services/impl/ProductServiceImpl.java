package com.service.ms.product.services.impl;

import com.service.ms.product.exceptions.CategoryNotFoundException;
import com.service.ms.product.exceptions.ProductNotFoundException;
import com.service.ms.product.mappers.ProductMapper;
import com.service.ms.product.models.dtos.CreateProductDto;
import com.service.ms.product.models.dtos.ProductResponseDto;
import com.service.ms.product.models.dtos.UpdateProductDto;
import com.service.ms.product.models.entities.Product;
import com.service.ms.product.repositories.CategoryRepository;
import com.service.ms.product.repositories.ProductRepository;
import com.service.ms.product.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    // DI
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        return productRepository.findById(id)
                .map(product -> productMapper.toProductResponseDto(product))
                .orElseThrow(() -> new ProductNotFoundException("No se encontro el producto con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> productMapper.toProductResponseDto(product))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {
        // Verificar que la categoría existe
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("No se encontro la categoria con id: " + categoryId));

        // Buscar productos por categoryId
        return productRepository.findByCategory_Id(categoryId)
                .stream()
                .map(product -> productMapper.toProductResponseDto(product))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponseDto create(CreateProductDto createProductDto) {
        // Verificar que la categoria existe
        var category = categoryRepository.findById(createProductDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("No se encontro la categoria con id: " + createProductDto.getCategoryId()));

        // Usar el mapper para convertir DTO a entidad
        Product product = productMapper.toProduct(createProductDto);
        product.setCategory(category);

        // Guardar el producto en la base de datos
        Product savedProduct = productRepository.save(product);

        // Convertir la entidad guardada a DTO de respuesta
        return productMapper.toProductResponseDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDto updateById(Long id, UpdateProductDto updateProductDto) {
        // Buscar el producto existente
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontro el producto con id: " + id));

        // Si se proporciona una nueva categoria, verificar que existe y setearla
        if (updateProductDto.getCategoryId() != null) {
            var category = categoryRepository.findById(updateProductDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("No se encontro la categoria con id: " + updateProductDto.getCategoryId()));
            existingProduct.setCategory(category);
        }

        // Usar el mapper para actualizar la entidad existente con los datos del DTO
        // Toma los datos nuevos del updateProductDto y los inyecta dentro del objeto existingProduct
        productMapper.updateProductFromDto(updateProductDto, existingProduct);

        // Guardar los cambios en la base de datos
        Product updatedProduct = productRepository.save(existingProduct);

        // Convertir la entidad actualizada a DTO de respuesta
        return productMapper.toProductResponseDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Buscar el producto existente
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontro el producto con id: " + id));

        // Eliminar el producto
        productRepository.delete(existingProduct);
    }
}