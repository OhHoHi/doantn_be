package com.example.doantn.repository;

import com.example.doantn.dto.MonthlyRevenueDTO;
import com.example.doantn.dto.ProductRevenueDTO;
import com.example.doantn.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems")
//    List<Order> findAllWithOrderItems();
//List<Order> findByUserId(Long userId);


    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.user.id = :userId")
    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.status = 0")
    List<Order> findByStatusZero();

    @Query("SELECT o FROM Order o WHERE o.status != 0")
    List<Order> findByStatusNotZero();

    @Query("SELECT o FROM Order o WHERE o.status > 0 AND o.status < 3")
    List<Order> findByStatusBetweenOneAndThree();

    @Query("SELECT o FROM Order o WHERE  o.status > 2")
    List<Order> findByStatusOutsideOneToThree(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = 0")
    List<Order> findByUserIdAndStatusZero(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status > 0 AND o.status < 3")
    List<Order> findByUserIdAndStatusBetweenOneAndThree(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status > 2)")
    List<Order> findByUserIdAndStatusOutsideOneToThree(Long userId , Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.status < 0")
    List<Order> findByStatusLessThanZero();

    @Query("SELECT o FROM Order o WHERE o.status < 0 AND o.user.id = :userId")
    List<Order> findByStatusLessThanZeroAndUserId(Long userId);

    @Query("SELECT new com.example.doantn.dto.MonthlyRevenueDTO(FUNCTION('MONTH', o.orderDate), FUNCTION('YEAR', o.orderDate), SUM(oi.quantity * oi.product.price)) " +
            "FROM Order o JOIN o.orderItems oi " +
            "WHERE o.status > 2 " + // Assuming you want to exclude orders with status 0
            "GROUP BY FUNCTION('MONTH', o.orderDate), FUNCTION('YEAR', o.orderDate) " +
            "ORDER BY FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate)")
    List<MonthlyRevenueDTO> findMonthlyRevenue();


    @Query("SELECT new com.example.doantn.dto.ProductRevenueDTO(oi.product.id, oi.product.name, SUM(oi.quantity * oi.product.price)) " +
            "FROM OrderItem oi JOIN oi.order o " +  // Thêm join với Order để có thể sử dụng điều kiện cho status
            "WHERE o.status > 2 " +  // Thêm điều kiện cho status > 2
            "GROUP BY oi.product.id, oi.product.name " +
            "ORDER BY SUM(oi.quantity * oi.product.price) DESC")
    List<ProductRevenueDTO> findTop10ProductsByRevenue(PageRequest pageRequest);


}
