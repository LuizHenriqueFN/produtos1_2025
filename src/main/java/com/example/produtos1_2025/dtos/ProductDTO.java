package com.example.produtos1_2025.dtos;

import com.example.produtos1_2025.entity.Category;
import com.example.produtos1_2025.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductDTO extends RepresentationModel<ProductDTO> {
    @Schema(description = "Database generated ID product")
    private Long id;
    @Schema(description = "Product name")
    @Size(min = 3, max=255, message = "Deve ter entre 3 e 255 caracteres")
    private String name;
    @Schema(description = "Product description")
    private String description;
    @Schema(description = "Product price")
    @Positive(message = "Preço deve ter valor positivo.")
    private double price;
    @Schema(description = "Product image url")
    private String imageUrl;
    @Schema(description = "Product categories")
    @NotEmpty(message = "Produto deve ter pelo menos 1 categoria")
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
