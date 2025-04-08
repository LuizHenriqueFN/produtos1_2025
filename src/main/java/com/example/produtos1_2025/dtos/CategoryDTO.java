package com.example.produtos1_2025.dtos;

import com.example.produtos1_2025.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Category enyity) {
        this.id = enyity.getId();
        this.name = enyity.getName();
    }
}
