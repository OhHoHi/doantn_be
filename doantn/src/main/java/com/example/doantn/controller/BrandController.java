package com.example.doantn.controller;

import com.example.doantn.entity.Brands;
import com.example.doantn.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private BrandsService brandService;

    @GetMapping("/getList")
    public ResponseEntity<List<Brands>> getAllBrands() {
        List<Brands> brands = brandService.getAllBrands();
        if (brands.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(brands);
        }
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }
}