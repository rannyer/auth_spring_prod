package com.example.posts.post_params.respositories;

import com.example.posts.post_params.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
