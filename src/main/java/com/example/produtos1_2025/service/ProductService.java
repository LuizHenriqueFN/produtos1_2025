package com.example.produtos1_2025.service;

import com.example.produtos1_2025.controller.ProductController;
import com.example.produtos1_2025.dtos.ProductDTO;
import com.example.produtos1_2025.entity.Category;
import com.example.produtos1_2025.entity.Product;
import com.example.produtos1_2025.repository.ProductRepository;
import com.example.produtos1_2025.service.exceptions.DatabaseException;
import com.example.produtos1_2025.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> list = productRepository.findAll(pageable);
        return list.map(product -> new ProductDTO(product)
                .add(linkTo(methodOn(ProductController.class).findAll(null)).withSelfRel())
                .add(linkTo(methodOn(ProductController.class).findById(product.getId())).withRel("Get a product")));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj = productRepository.findById(id);
        Product product = obj.orElseThrow(() -> new ResourceNotFound("Product not found " + id));
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        this.copyDto(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Product entity = productRepository.getReferenceById(id);
            this.copyDto(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Product not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFound("Product not found " + id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }



    private void copyDto(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());

        dto.getCategories().forEach(c -> entity.getCategories().add(new Category(c)));
    }
}