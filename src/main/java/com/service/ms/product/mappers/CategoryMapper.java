package com.service.ms.product.mappers;

import com.service.ms.product.models.dtos.CategoryResponseDto;
import com.service.ms.product.models.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // MapStruct mapeara id, name, description automaticamente por nombre
    // Convierte entidad a DTO de respuesta
    CategoryResponseDto toCategoryResponseDto(Category category);

    // Si se necesita convertir de DTO a entity en algun momento
    // Convierte DTO a entidad
    Category toEntity(CategoryResponseDto categoryResponseDto);
}
