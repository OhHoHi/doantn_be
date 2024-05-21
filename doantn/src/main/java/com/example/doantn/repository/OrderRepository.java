package com.example.doantn.repository;

import com.example.doantn.entity.Order;
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

    @Query("SELECT o FROM Order o WHERE o.status < 1 OR o.status > 2")
    List<Order> findByStatusOutsideOneToThree();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = 0")
    List<Order> findByUserIdAndStatusZero(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status > 0 AND o.status < 3")
    List<Order> findByUserIdAndStatusBetweenOneAndThree(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.status < 1 OR o.status > 2)")
    List<Order> findByUserIdAndStatusOutsideOneToThree(Long userId);
}
