package com.example.backend.service;

import com.example.backend.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private static final String URI = "http://localhost:3001/product/{productId}";
    private static final String SIMILAR_IDS_PATH = "/similarids";

    @Autowired
    RestTemplate restTemplate;

    @Cacheable(cacheNames="similarIds")
    public ResponseEntity<String[]> getSimilarIds(@PathVariable("productId") String productId) {
        HttpEntity<String> entity = buildHeaders();
        URI uri = buildURI(productId);
        return restTemplate.exchange(uri +SIMILAR_IDS_PATH, HttpMethod.GET, entity, String[].class);
    }

    @Cacheable(cacheNames="product")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        HttpEntity<String> entity = buildHeaders();
        URI uri = buildURI(productId);
        return restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Product.class);
    }

    private HttpEntity<String> buildHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private URI buildURI(String productId){
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("productId", productId);
        return new UriTemplate(URI).expand(uriVariables);
    }
}