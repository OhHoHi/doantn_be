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
}
