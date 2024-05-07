package com.example.doantn.controller;

import com.example.doantn.entity.CartItem;
import com.example.doantn.entity.Product;
import com.example.doantn.entity.User;
import com.example.doantn.service.CartItemService;
import com.example.doantn.service.ProductService;
import com.example.doantn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());
        // Kiểm tra xem user và product có tồn tại không
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng của người dùng chưa
        CartItem existingCartItem = cartItemService.getCartItemByUserAndProduct(user, product);
        if (existingCartItem != null) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng lên 1
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemService.updateCartItem(existingCartItem);
            return ResponseEntity.ok("Quantity increased for existing product in cart");
        } else {
            // Nếu sản phẩm chưa tồn tại, tạo một mục giỏ hàng mới
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            // Lưu mục giỏ hàng vào cơ sở dữ liệu
            cartItemService.addToCart(cartItem);
            return ResponseEntity.ok("Product added to cart successfully");
        }

    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemService.getCartItemsSortedByCreatedAt(userId);
        return ResponseEntity.ok(cartItems);
    }
//    @GetMapping("/items/{userId}")
//    public ResponseEntity<List<Product>> getCartItemsByUserId(@PathVariable Long userId) {
//        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);
//        List<Product> products = cartItems.stream().map(CartItem::getProduct).collect(Collectors.toList());
//        return ResponseEntity.ok(products);
//    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long itemId) {
        boolean removed = cartItemService.removeItemFromCart(itemId);
        if (removed) {
            return ResponseEntity.ok("Item removed from cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in cart");
        }
    }

    // Tăng số lượng sản phẩm trong giỏ hàng
    @PutMapping("/{cartItemId}/increaseQuantity")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long cartItemId) {
        cartItemService.increaseCartItemQuantity(cartItemId);
        return ResponseEntity.ok("Quantity increased successfully.");
    }

    // Giảm số lượng sản phẩm trong giỏ hàng
    @PutMapping("/{cartItemId}/decreaseQuantity")
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long cartItemId) {
        cartItemService.decreaseCartItemQuantity(cartItemId);
        return ResponseEntity.ok("Quantity decreased successfully.");
    }
//@DeleteMapping("/items/{productId}")
//public ResponseEntity<String> removeItemFromCart(@PathVariable Long productId) {
//    boolean removed = cartItemService.removeItemFromCartByProductId(productId);
//    if (removed) {
//        return ResponseEntity.ok("Item removed from cart successfully");
//    } else {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in cart");
//    }
//}


}
