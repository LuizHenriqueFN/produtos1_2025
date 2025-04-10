package com.example.produtos1_2025.dtos;

import com.example.produtos1_2025.entity.Category;
import com.example.produtos1_2025.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Set<CategoryDTO> categories = new HashSet<>();


    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();

        entity.getCategories().forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(c -> this.categories.add(new CategoryDTO(c)));
    }

}
