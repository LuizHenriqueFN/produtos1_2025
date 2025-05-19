package com.example.produtos1_2025.util;

import com.example.produtos1_2025.dtos.ProductDTO;
import com.example.produtos1_2025.entity.Category;
import com.example.produtos1_2025.entity.Product;

public class Factory {

    public static Product createProduct(){
        Product p = new Product();
        p.setName("Produto Teste");
        p.setPrice(1000);
        p.setImageUrl("https://img.com/test.png");
        p.setDescription("Teste");
        p.getCategories().add(new Category("Livros", 1L));
        return p;
    }
    
    public static ProductDTO createProductDTO(){
        Product p = createProduct();
        return new ProductDTO(p);
    }

}