package com.example.doantn.controller;

import com.example.doantn.Response.UploadResponse;
import com.example.doantn.dto.ProductDTO;
import com.example.doantn.entity.CartItem;
import com.example.doantn.entity.Product;
import com.example.doantn.service.CartItemService;
import com.example.doantn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/addProduct")
    public ResponseEntity<UploadResponse> addProduct(@ModelAttribute ProductDTO productDTO) {
        UploadResponse response = productService.addProduct(productDTO);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/listProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId,
                                                 @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(productId, productDTO);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
//        boolean isDeleted = productService.deleteProduct(productId);
//        if (isDeleted) {
//            return ResponseEntity.ok("Product with ID " + productId + " has been deleted successfully.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        // Kiểm tra xem sản phẩm có tồn tại trong bất kỳ giỏ hàng nào không
        List<CartItem> cartItems = cartItemService.getCartItemsByProductId(productId);
        if (!cartItems.isEmpty()) {
            // Nếu sản phẩm tồn tại trong giỏ hàng, xóa nó khỏi tất cả các giỏ hàng
            cartItems.forEach(cartItem -> cartItemService.removeFromCart(cartItem));
        }
        // Sau đó xóa sản phẩm
        boolean isDeleted = productService.deleteProduct(productId);
        if (isDeleted) {
            return ResponseEntity.ok("Product with ID " + productId + " has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        // Tạo đường dẫn đến thư mục chứa ảnh
        Path imagePath = Paths.get("D:/Student/java/testimg/image/").resolve(imageName);
        try {
            // Đọc dữ liệu của ảnh từ tệp
            byte[] imageData = Files.readAllBytes(imagePath);
            // Trả về dữ liệu của ảnh dưới dạng phản hồi
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // hoặc MediaType.IMAGE_PNG tùy vào loại ảnh của bạn
                    .body(imageData);
        } catch (IOException e) {
            // Xử lý ngoại lệ nếu không thể đọc được tệp ảnh
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
