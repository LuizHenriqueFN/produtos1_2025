package com.example.produtos1_2025.controller;

import com.example.produtos1_2025.dtos.ProductDTO;
import com.example.produtos1_2025.service.ProductService;
import com.example.produtos1_2025.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ProductDTO productDto;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 200L;
        productDto = Factory.createProductDTO();
        productDto.setId(existingId);
        page = new PageImpl<>(List.of(productDto,productDto));
    }


    @Test
    void findAllShoudReturnAllPage() throws Exception {
        when(productService.findAll(any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(get("/product")
                .accept("application/json")
        );

        resultActions.andExpect(status().isOk());
    }

    @Test
        void findByShouldReturnProductWhenIdExists() throws Exception {
            when(productService.findById(existingId)).thenReturn(productDto);

            ResultActions resultActions = mockMvc.perform(get("/product/{id}", existingId)
                    .accept("application/json")
            );

            resultActions.andExpect(status().isOk());
            resultActions.andExpect(jsonPath("$.id").value(existingId));
            resultActions.andExpect(jsonPath("$.name").value(productDto.getName()));
            resultActions.andExpect(jsonPath("$.description").value(productDto.getDescription()));
    }
}
