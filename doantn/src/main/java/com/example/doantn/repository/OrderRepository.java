package com.example.doantn.repository;

import com.example.doantn.dto.MonthlyRevenueDTO;
import com.example.doantn.dto.ProductRevenueDTO;
import com.example.doantn.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    @Query("SELECT o FROM Order o WHERE o.status < 1 OR o.status > 2")
    List<Order> findByStatusOutsideOneToThree();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = 0")
    List<Order> findByUserIdAndStatusZero(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status > 0 AND o.status < 3")
    List<Order> findByUserIdAndStatusBetweenOneAndThree(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status < 1 OR o.status > 2)")
    List<Order> findByUserIdAndStatusOutsideOneToThree(Long userId);


    @Query("SELECT new com.example.doantn.dto.MonthlyRevenueDTO(FUNCTION('MONTH', o.orderDate), FUNCTION('YEAR', o.orderDate), SUM(oi.quantity * oi.product.price)) " +
            "FROM Order o JOIN o.orderItems oi " +
            "WHERE o.status <> 0 " + // Assuming you want to exclude orders with status 0
            "GROUP BY FUNCTION('MONTH', o.orderDate), FUNCTION('YEAR', o.orderDate) " +
            "ORDER BY FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate)")
    List<MonthlyRevenueDTO> findMonthlyRevenue();


    @Query("SELECT new com.example.doantn.dto.ProductRevenueDTO(oi.product.id, oi.product.name, SUM(oi.quantity * oi.product.price)) " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product.id, oi.product.name " +
            "ORDER BY SUM(oi.quantity * oi.product.price) DESC")
    List<ProductRevenueDTO> findTop10ProductsByRevenue(PageRequest pageRequest);
}
