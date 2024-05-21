package com.example.doantn.repository;

import com.example.doantn.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long> {
    Brands findByName(String name);

    List<Brands> findAll();

}