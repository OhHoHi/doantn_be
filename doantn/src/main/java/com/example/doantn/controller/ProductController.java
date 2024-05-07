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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestBody Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        String sort = requestParams.get("sort");
        List<Product> products;

        // Tính toán offset dựa trên trang hiện tại và số lượng sản phẩm trên mỗi trang
        int offset = page * size;

        // Kiểm tra nếu tham số sort được truyền vào từ frontend
        if (sort != null) {
            // Sắp xếp sản phẩm dựa trên giá trị của tham số sort
            switch (sort) {
                case "id_asc":
                    products = productService.getAllProductsSortedByIdAsc(offset, size);
                    break;
                case "id_desc":
                    products = productService.getAllProductsSortedByIdDesc(offset, size);
                    break;
                case "price_asc":
                    products = productService.getAllProductsSortedByPriceAsc(offset, size);
                    break;
                case "price_desc":
                    products = productService.getAllProductsSortedByPriceDesc(offset, size);
                    break;
                case "name_asc":
                    products = productService.getAllProductsSortedByNameAsc(offset, size);
                    break;
                case "name_desc":
                    products = productService.getAllProductsSortedByNameDesc(offset, size);
                    break;
                default:
                    // Trường hợp không hợp lệ, trả về danh sách sản phẩm mặc định
                    products = productService.getAllProductsPhanTrang(offset, size);
                    break;
            }
        } else {
            // Nếu không có tham số sort, trả về danh sách sản phẩm mặc định
            products = productService.getAllProductsPhanTrang(offset, size);
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @GetMapping("/searchAndSort")
    public ResponseEntity<List<Product>> searchAndSortProducts(
            @RequestParam(defaultValue = "id_asc") String sort,
            @RequestParam(required = false) String searchTerm
    ) {
        List<Product> searchResults;

        // Thực hiện tìm kiếm dựa trên searchTerm (nếu có)
        if (searchTerm != null && !searchTerm.isEmpty()) {
            searchResults = productService.searchProductsByName(searchTerm);
        } else {
            // Nếu không có searchTerm, trả về toàn bộ sản phẩm
            searchResults = productService.getAllProducts();
        }

        // Thực hiện sắp xếp dựa trên sort
        switch (sort) {
            case "id_asc":
                Collections.sort(searchResults, Comparator.comparing(Product::getId));
                break;
            case "id_desc":
                Collections.sort(searchResults, Comparator.comparing(Product::getId).reversed());
                break;
            case "price_asc":
                Collections.sort(searchResults, Comparator.comparing(Product::getPrice));
                break;
            case "price_desc":
                Collections.sort(searchResults, Comparator.comparing(Product::getPrice).reversed());
                break;
            // Các trường hợp sắp xếp khác
        }

        // Trả về kết quả tìm kiếm và sắp xếp
        return ResponseEntity.ok(searchResults);
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
