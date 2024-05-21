package com.example.doantn.repository;


import com.example.doantn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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

    //List<Product> findByBrandsId(Long brandId);
    List<Product> findByBrandsId(Long brandId, Pageable pageable);

    // Phương thức để tìm kiếm sản phẩm theo tên sản phẩm
    List<Product> findByNameContaining(String productName ,Pageable pageable);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByBrandsIdAndNameContaining(Long brandId , String productName);
    List<Product> findByBrandsIdAndPriceBetween(Long brandId, BigDecimal minPrice, BigDecimal maxPrice);


    List<Product> findByDiemCanBangLessThan(int diemCanBang,Pageable pageable);

    List<Product> findByDiemCanBangBetween(int minDiemCanBang, int maxDiemCanBang,Pageable pageable);

    List<Product> findByDiemCanBangGreaterThan(int diemCanBang,Pageable pageable);

    List<Product> findByBrandsIdAndDiemCanBang(Long brandId, int diemCanBang, Pageable pageable);

    // Tìm kiếm sản phẩm theo brandId và diemCanBang nhỏ hơn giá trị đã cho
    List<Product> findByBrandsIdAndDiemCanBangLessThan(Long brandId, int diemCanBang, Pageable pageable);

    // Tìm kiếm sản phẩm theo brandId và diemCanBang nằm trong khoảng giá trị đã cho
    List<Product> findByBrandsIdAndDiemCanBangBetween(Long brandId, int minDiemCanBang, int maxDiemCanBang, Pageable pageable);

    // Tìm kiếm sản phẩm theo brandId và diemCanBang lớn hơn giá trị đã cho
    List<Product> findByBrandsIdAndDiemCanBangGreaterThan(Long brandId, int diemCanBang, Pageable pageable);

    // Tìm kiếm sản phẩm có điểm cân bằng dưới 285 và trong khoảng giá cho trước
    List<Product> findByDiemCanBangLessThanAndPriceBetween(int diemCanBang, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // Tìm kiếm sản phẩm có điểm cân bằng từ 285 đến 295 và trong khoảng giá cho trước
    List<Product> findByDiemCanBangBetweenAndPriceBetween(int minDiemCanBang, int maxDiemCanBang, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // Tìm kiếm sản phẩm có điểm cân bằng trên 295 và trong khoảng giá cho trước
    List<Product> findByDiemCanBangGreaterThanAndPriceBetween(int diemCanBang, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);


    // Tìm kiếm sản phẩm theo brand, có điểm cân bằng dưới 285 và trong khoảng giá cho trước
    List<Product> findByBrandsIdAndDiemCanBangLessThanAndPriceBetween(Long brandId, int diemCanBang, BigDecimal minPrice, BigDecimal maxPrice);

    // Tìm kiếm sản phẩm theo brand, có điểm cân bằng từ 285 đến 295 và trong khoảng giá cho trước
    List<Product> findByBrandsIdAndDiemCanBangBetweenAndPriceBetween(Long brandId, int minDiemCanBang, int maxDiemCanBang, BigDecimal minPrice, BigDecimal maxPrice);

    // Tìm kiếm sản phẩm theo brand, có điểm cân bằng trên 295 và trong khoảng giá cho trước
    List<Product> findByBrandsIdAndDiemCanBangGreaterThanAndPriceBetween(Long brandId, int diemCanBang, BigDecimal minPrice, BigDecimal maxPrice);




}
