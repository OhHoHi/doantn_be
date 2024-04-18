package com.example.doantn.controller;

import com.example.doantn.Response.UploadResponse;
import com.example.doantn.dto.ProductDTO;
import com.example.doantn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<UploadResponse> addProduct(@ModelAttribute ProductDTO productDTO) {
        UploadResponse response = productService.addProduct(productDTO);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
