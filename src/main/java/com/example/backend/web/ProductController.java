package com.example.backend.web;

import com.example.backend.dto.Product;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/product/{productId}/similar")
    public ResponseEntity <?> getSimilarProductsDetail(@PathVariable("productId") String productId) {

        try {
            List<Product> listProducts = new ArrayList<>();
            List<String> similarIds = Arrays.asList(productService.getSimilarIds(productId).getBody());

            for (String id : similarIds) {
                listProducts.add(productService.getProduct(id).getBody());
            }

            return new ResponseEntity<>(listProducts, HttpStatus.OK);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}