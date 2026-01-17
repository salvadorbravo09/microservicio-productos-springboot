package com.service.ms.product.services;

import com.service.ms.product.models.dtos.CreateProductDto;
import com.service.ms.product.models.dtos.ProductResponseDto;
import com.service.ms.product.models.dtos.UpdateProductDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto findById(Long id);

    List<ProductResponseDto> findAll();

    List<ProductResponseDto> findByCategoryId(Long categoryId);

    ProductResponseDto create(CreateProductDto createProductDto);

    ProductResponseDto updateById(Long id, UpdateProductDto updateProductDto);

    void deleteById(Long id);
}
