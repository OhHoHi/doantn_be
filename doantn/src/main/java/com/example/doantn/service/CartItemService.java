package com.example.doantn.service;

import com.example.doantn.entity.CartItem;
import com.example.doantn.entity.Product;
import com.example.doantn.entity.User;
import com.example.doantn.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(CartItem cartItem) {
        cartItem.setCreatedAt(LocalDateTime.now()); // Thiết lập thời gian tạo mới khi thêm vào giỏ hàng
        cartItemRepository.save(cartItem);
    }
//    public void updateTimeCartItem(CartItem cartItem) {
//        // Cập nhật thời gian của mục giỏ hàng
//        cartItem.setCreatedAt(LocalDateTime.now());
//        cartItemRepos itory.save(cartItem);
//    }
    // Lấy danh sách sản phẩm trong giỏ hàng của người dùng
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
    public List<CartItem> getCartItemsSortedByCreatedAt(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return cartItems;
    }

    // xóa 1 sản phẩm ra khỏi giỏ hàng
    public boolean removeItemFromCart(Long itemId) {
        // Kiểm tra xem sản phẩm có tồn tại trong giỏ hàng không
        if (!cartItemRepository.existsById(itemId)) {
            return false; // Sản phẩm không tồn tại trong giỏ hàng
        }

        // Xóa sản phẩm từ giỏ hàng
        cartItemRepository.deleteById(itemId);
        return true; // Xóa thành công
    }
    // Lấy mục giỏ hàng dựa trên người dùng và sản phẩm
    public CartItem getCartItemByUserAndProduct(User user, Product product) {
        return cartItemRepository.findByUserAndProduct(user, product);
    }
    public void updateCartItem(CartItem cartItem) {
        cartItem.setCreatedAt(LocalDateTime.now());
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByProductId(Long productId) {
        return cartItemRepository.findByProduct_Id(productId);
    }

    public void removeFromCart(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    // Tăng số lượng sản phẩm trong giỏ hàng
    public void increaseCartItemQuantity(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        } else {
            // Xử lý trường hợp không tìm thấy mục giỏ hàng
        }
    }

    // Giảm số lượng sản phẩm trong giỏ hàng
    public void decreaseCartItemQuantity(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() - 1;
            // Kiểm tra nếu số lượng mới là 0, hãy xóa mục giỏ hàng
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            // Xử lý trường hợp không tìm thấy mục giỏ hàng
        }
    }

//    public boolean removeItemFromCartByProductId(Long productId) {
//        // Kiểm tra xem sản phẩm có tồn tại trong giỏ hàng không
//        CartItem cartItem = cartItemRepository.findByProductId(productId);
//        if (cartItem == null) {
//            return false; // Sản phẩm không tồn tại trong giỏ hàng
//        }
//
//        // Xóa sản phẩm từ giỏ hàng
//        cartItemRepository.delete(cartItem);
//        return true; // Xóa thành công
//    }
}