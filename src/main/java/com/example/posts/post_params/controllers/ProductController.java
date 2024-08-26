package com.example.posts.post_params.controllers;

import com.example.posts.post_params.domain.product.Product;
import com.example.posts.post_params.domain.product.ProductRequestDTO;
import com.example.posts.post_params.domain.product.ProductResponseDTO;
import com.example.posts.post_params.respositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
    return ResponseEntity.ok(productRepository.findAll().stream().map(ProductResponseDTO::new).toList());
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        Product newProduct = new Product(body);

        this.productRepository.save(newProduct);
        return ResponseEntity.ok().build();
    }
}
