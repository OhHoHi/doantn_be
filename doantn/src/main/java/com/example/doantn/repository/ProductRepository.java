package com.example.doantn.repository;


import com.example.doantn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdAsc(Pageable pageable);
    List<Product> findAllByOrderByIdDesc(Pageable pageable);
    List<Product> findAllByOrderByPriceAsc(Pageable pageable);
    List<Product> findAllByOrderByPriceDesc(Pageable pageable);
    List<Product> findAllByOrderByNameAsc(Pageable pageable);
    List<Product> findAllByOrderByNameDesc(Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String name);

}
