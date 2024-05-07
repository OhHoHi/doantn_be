package com.example.doantn.repository;
import com.example.doantn.entity.CartItem;
import com.example.doantn.entity.Product;
import com.example.doantn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Lấy danh sách các mục trong giỏ hàng của người dùng dựa trên người dùng
    List<CartItem> findByUserId(Long userId);

    CartItem findByProductId(Long productId);

    List<CartItem> findByProduct_Id(Long productId);


    // Lấy một mục trong giỏ hàng của người dùng dựa trên ID
    CartItem findByIdAndUser(Long id, User user);

    CartItem findByUserAndProduct(User user, Product product);
    List<CartItem> findByUserIdOrderByCreatedAtDesc(Long userId);


}