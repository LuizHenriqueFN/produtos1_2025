package com.example.produtos1_2025.service;

import com.example.produtos1_2025.entity.Category;
import com.example.produtos1_2025.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}