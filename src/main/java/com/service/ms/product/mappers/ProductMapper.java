package com.service.ms.product.mappers;

import com.service.ms.product.models.dtos.CreateProductDto;
import com.service.ms.product.models.dtos.ProductResponseDto;
import com.service.ms.product.models.dtos.UpdateProductDto;
import com.service.ms.product.models.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

    // Entity -> Response DTO
    // Convierte entidad a DTO de respuesta
    ProductResponseDto toProductResponseDto(Product product);

    // Create DTO -> Entity
    // Convierte DTO de creacion a entidad (status = true por defecto)
    @Mapping(target = "id", ignore = true) // Ignorar el id ya que es generado por la base de datos
    @Mapping(target = "status", constant = "true") // Por defecto el estado es true al crear un producto
    @Mapping(target = "category", ignore = true) // El DTO trae un Long, la entidad quiere una Category. Se setea en el Service
    Product toProduct(CreateProductDto createProductDto);

    // Update DTO -> Entity (Actualización sobre objeto existente)
    // Actualiza una entidad existente sin crear una nueva
    @Mapping(target = "id", ignore = true) // El ID no se toca en update
    @Mapping(target = "category", ignore = true) // Se busca y setea en el Service si cambió
    void updateProductFromDto(UpdateProductDto updateProductDto, @MappingTarget Product product);
}
