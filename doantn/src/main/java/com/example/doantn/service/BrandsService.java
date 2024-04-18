package com.example.doantn.service;

import com.example.doantn.entity.Brands;
import com.example.doantn.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandsService {

    @Autowired
    private BrandsRepository brandsRepository;

    public Brands getOrCreateBrands(String name) {
        Brands brands = brandsRepository.findByName(name);
        if (brands == null) {
            brands = new Brands();
            brands.setName(name);
            brands = brandsRepository.save(brands);
        }
        return brands;
    }
}