package com.service.ms.product.repositories;

import com.service.ms.product.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Category --> campo category en la entidad Product
     * _Id --> la propiedad id de la entidad Category
     * **/
    List<Product> findByCategory_Id(Long categoryId);
}
