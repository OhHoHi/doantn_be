package com.example.doantn.service;

import com.example.doantn.Response.UploadResponse;
import com.example.doantn.dto.ProductDTO;
import com.example.doantn.entity.Brands;
import com.example.doantn.entity.Product;
import com.example.doantn.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandsService brandsService;

    public UploadResponse addProduct(ProductDTO productDTO) {
        try {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : productDTO.getImages()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path path = Paths.get("D:/Student/java/testimg/image/" + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                imageUrls.add(fileName);
            }

            // Lấy thông tin của brands hoặc thêm mới nếu nó không tồn tại
            Brands brands = brandsService.getOrCreateBrands(productDTO.getBrandsName());

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setImageUrls(imageUrls);
            product.setBrands(brands);
            product.setPrice(productDTO.getPrice());
            product.setStatus(productDTO.getStatus());
            product.setColor(productDTO.getColor());
            product.setChatLieuKhungVot(productDTO.getChatLieuKhungVot());
            product.setChatLieuThanVot(productDTO.getChatLieuThanVot());
            product.setTrongLuong(productDTO.getTrongLuong());
            product.setDoCung(productDTO.getDoCung());
            product.setDiemCanBang(productDTO.getDiemCanBang());
            product.setChieuDaiVot(productDTO.getChieuDaiVot());
            product.setMucCangToiDa(productDTO.getMucCangToiDa());
            product.setChuViCanCam(productDTO.getChuViCanCam());
            product.setTrinhDoChoi(productDTO.getTrinhDoChoi());
            product.setNoiDungChoi(productDTO.getNoiDungChoi());
            productRepository.save(product);

            // Tạo một đối tượng UploadResponse với thông điệp và danh sách tên ảnh
            UploadResponse response = new UploadResponse();
            response.setMessage("Product added successfully with images");
            response.setImageUrls(imageUrls);

            return response;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productRepository.delete(product);
            return true;
        }
        return false; // Trả về false nếu không tìm thấy sản phẩm cần xóa
    }

    public Product updateProduct(Long productId, ProductDTO productDTO) {


        // Tìm kiếm sản phẩm dựa trên ID
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            // Nếu sản phẩm tồn tại
            Product existingProduct = optionalProduct.get();

            // Kiểm tra nếu productDTO chứa tên thương hiệu mới
            if (productDTO.getBrandsName() != null) {
                // Lấy thông tin thương hiệu mới hoặc tạo mới nếu chưa tồn tại
                Brands brands = brandsService.getOrCreateBrands(productDTO.getBrandsName());
                // Gán thương hiệu mới cho sản phẩm
                existingProduct.setBrands(brands);
            }

            // Cập nhật thông tin sản phẩm từ ProductDTO
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStatus(productDTO.getStatus());
            existingProduct.setColor(productDTO.getColor());
            existingProduct.setChatLieuKhungVot(productDTO.getChatLieuKhungVot());
            existingProduct.setChatLieuThanVot(productDTO.getChatLieuThanVot());
            existingProduct.setTrongLuong(productDTO.getTrongLuong());
            existingProduct.setDoCung(productDTO.getDoCung());
            existingProduct.setDiemCanBang(productDTO.getDiemCanBang());
            existingProduct.setChieuDaiVot(productDTO.getChieuDaiVot());
            existingProduct.setMucCangToiDa(productDTO.getMucCangToiDa());
            existingProduct.setChuViCanCam(productDTO.getChuViCanCam());
            existingProduct.setTrinhDoChoi(productDTO.getTrinhDoChoi());
            existingProduct.setNoiDungChoi(productDTO.getNoiDungChoi());
            // Lưu thông tin sản phẩm đã cập nhật và trả về
            return productRepository.save(existingProduct);
        } else {
            // Nếu không tìm thấy sản phẩm, có thể trả về null hoặc xử lý theo yêu cầu cụ thể
            return null;
        }
    }

}
