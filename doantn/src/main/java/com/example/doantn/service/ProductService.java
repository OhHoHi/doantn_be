package com.example.doantn.service;

import com.example.doantn.Response.UploadResponse;
import com.example.doantn.dto.ProductDTO;
import com.example.doantn.entity.Brands;
import com.example.doantn.entity.Product;
import com.example.doantn.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private BrandsService brandsService;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
            product.setDiemCanBang(Integer.parseInt(productDTO.getDiemCanBang()));
            product.setChieuDaiVot(productDTO.getChieuDaiVot());
            product.setMucCangToiDa(productDTO.getMucCangToiDa());
            product.setChuViCanCam(productDTO.getChuViCanCam());
            product.setTrinhDoChoi(productDTO.getTrinhDoChoi());
            product.setNoiDungChoi(productDTO.getNoiDungChoi());

            // Thêm totalQuantity từ ProductDTO
            product.setTotalQuantity(productDTO.getTotalQuantity());

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
    public List<Product> getAllProductsPhanTrang(int offset, int size) {
        return productRepository.findAll(PageRequest.of(offset / size, size)).getContent();
    }

    public List<Product> getAllProductsSortedByIdAsc(int offset, int size) {
        return productRepository.findAllByOrderByIdAsc(PageRequest.of(offset / size, size));
    }

    public List<Product> getAllProductsSortedByIdDesc(int offset, int size) {
        return productRepository.findAllByOrderByIdDesc(PageRequest.of(offset / size, size));
    }

    public List<Product> getAllProductsSortedByPriceAsc(int offset, int size) {
        return productRepository.findAllByOrderByPriceAsc(PageRequest.of(offset / size, size));
    }

    public List<Product> getAllProductsSortedByPriceDesc(int offset, int size) {
        return productRepository.findAllByOrderByPriceDesc(PageRequest.of(offset / size, size));
    }

    public List<Product> getAllProductsSortedByNameAsc(int offset, int size) {
        return productRepository.findAllByOrderByNameAsc(PageRequest.of(offset / size, size));
    }

    public List<Product> getAllProductsSortedByNameDesc(int offset, int size) {
        return productRepository.findAllByOrderByNameDesc(PageRequest.of(offset / size, size));
    }
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
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
            existingProduct.setDiemCanBang(Integer.parseInt(productDTO.getDiemCanBang()));
            existingProduct.setChieuDaiVot(productDTO.getChieuDaiVot());
            existingProduct.setMucCangToiDa(productDTO.getMucCangToiDa());
            existingProduct.setChuViCanCam(productDTO.getChuViCanCam());
            existingProduct.setTrinhDoChoi(productDTO.getTrinhDoChoi());
            existingProduct.setNoiDungChoi(productDTO.getNoiDungChoi());

            // Kiểm tra và cập nhật totalQuantity nếu có
            if (productDTO.getTotalQuantity() != 0) {
                existingProduct.setTotalQuantity(productDTO.getTotalQuantity());
                // Cập nhật trạng thái của sản phẩm dựa trên totalQuantity
                if (productDTO.getTotalQuantity() == 0) {
                    existingProduct.setStatus("Hết hàng");
                } else {
                    existingProduct.setStatus("Còn hàng");
                }
            }


            // Lưu thông tin sản phẩm đã cập nhật và trả về
            return productRepository.save(existingProduct);
        } else {
            // Nếu không tìm thấy sản phẩm, có thể trả về null hoặc xử lý theo yêu cầu cụ thể
            return null;
        }
    }
    public Product getProductById(Long productId) {
        // Sử dụng productRepository để tìm kiếm sản phẩm trong cơ sở dữ liệu
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
    }

//    public List<Product> getProductsByBrand(Long brandId) {
//        return productRepository.findByBrandsId(brandId);
//    }

    public List<Product> getProductsByBrandId(Long brandId, int page, int size) {
        return productRepository.findByBrandsId(brandId, PageRequest.of(page, size));
    }

    public List<Product> getProductsByBrandIdAndSort(Long brandId, int page, int size, String sort) {
        List<Product> products = productRepository.findByBrandsId(brandId, PageRequest.of(page, size)); // Lấy danh sách sản phẩm theo brandId

        // Sắp xếp danh sách sản phẩm nếu tham số sort được truyền vào từ frontend
        if (sort != null) {
            switch (sort) {
                case "id_asc":
                    Collections.sort(products, Comparator.comparing(Product::getId));
                    break;
                case "id_desc":
                    Collections.sort(products, Comparator.comparing(Product::getId).reversed());
                    break;
                case "price_asc":
                    Collections.sort(products, Comparator.comparing(Product::getPrice));
                    break;
                case "price_desc":
                    Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
                    break;
                case "name_asc":
                    Collections.sort(products, Comparator.comparing(Product::getName));
                    break;
                case "name_desc":
                    Collections.sort(products, Comparator.comparing(Product::getName).reversed());
                    break;
                default:
                    // Không sắp xếp
                    break;
            }
        }

        return products;
    }
    public List<Product> getAllProductsPhanTrang1(String sort, int offset, int size) {
        List<Product> products = productRepository.findAll(PageRequest.of(offset / size, size)).getContent();
        // Sắp xếp danh sách sản phẩm nếu tham số sort được truyền vào từ frontend
        if (sort != null) {
            switch (sort) {
                case "id_asc":
                    Collections.sort(products, Comparator.comparing(Product::getId));
                    break;
                case "id_desc":
                    Collections.sort(products, Comparator.comparing(Product::getId).reversed());
                    break;
                case "price_asc":
                    Collections.sort(products, Comparator.comparing(Product::getPrice));
                    break;
                case "price_desc":
                    Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
                    break;
                case "name_asc":
                    Collections.sort(products, Comparator.comparing(Product::getName));
                    break;
                case "name_desc":
                    Collections.sort(products, Comparator.comparing(Product::getName).reversed());
                    break;
                default:
                    // Không sắp xếp
                    break;
            }
        }
        return products;
    }
    public List<Product> searchProductByNameAndSort(String productName , int page, int size, String sort) {
        // Triển khai logic để tìm kiếm sản phẩm theo tên sản phẩm
        // Ví dụ: tìm kiếm trong cơ sở dữ liệu
        List<Product> searchedProducts = productRepository.findByNameContaining(productName , PageRequest.of(page, size));

        // Sắp xếp danh sách sản phẩm nếu tham số sort được truyền vào từ frontend
        if (sort != null) {
            switch (sort) {
                case "id_asc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getId));
                    break;
                case "id_desc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getId).reversed());
                    break;
                case "price_asc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getPrice));
                    break;
                case "price_desc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getPrice).reversed());
                    break;
                case "name_asc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getName));
                    break;
                case "name_desc":
                    Collections.sort(searchedProducts, Comparator.comparing(Product::getName).reversed());
                    break;
                default:
                    // Không sắp xếp
                    break;
            }
        }
        return searchedProducts;
    }

//    public List<Product> searchProduct(Long brandId, int page, int size, String sort, String productName, BigDecimal minPrice, BigDecimal maxPrice) {
//        List<Product> products;
//
//        // Tìm kiếm theo brand
//        if (productName == null && minPrice == null && maxPrice == null) {
//            products = productRepository.findByBrandsId(brandId, PageRequest.of(page, size));
//        }
//        // Tìm kiếm theo tên
//        else if (brandId == null && minPrice == null && maxPrice == null) {
//            // Tìm kiếm theo tên và brand
//            if (productName != null) {
//                products = productRepository.findByBrandsIdAndNameContaining(productName, brandId);
//            } else {
//                products = productRepository.findByNameContaining(productName, PageRequest.of(page, size));
//            }
//        }
//        // Tìm kiếm theo giá
//        else if (productName == null && brandId == null) {
//            products = productRepository.findByPriceBetween(minPrice, maxPrice);
//        }
//        // Tìm kiếm theo brand + giá
//        else if (productName == null) {
//            products = productRepository.findByBrandsIdAndPriceBetween(brandId, minPrice, maxPrice);
//        }
//        // Trường hợp còn lại, trả về danh sách sản phẩm mặc định
//        else {
//            products = productRepository.findAll(PageRequest.of(page, size)).getContent();
//        }
//
//        return products;
//    }
public List<Product> searchProduct(Long brandId, int page, int size, String sort, String productName, BigDecimal minPrice, BigDecimal maxPrice , int diemCanBang) {
    List<Product> products;

    if(productName == null && minPrice == null && maxPrice == null && brandId == null && diemCanBang == 0 ){
         products = productRepository.findAll(PageRequest.of(page, size)).getContent();
    }
    // Tìm kiếm theo diem can bang
    else if (productName == null && minPrice == null && maxPrice == null && brandId == null && diemCanBang != 0) {
        if (diemCanBang <= 285) {
            products = productRepository.findByDiemCanBangLessThan(diemCanBang, PageRequest.of(page, size));
        } else if (diemCanBang <= 295) {
            products = productRepository.findByDiemCanBangBetween(285, 295, PageRequest.of(page, size));
        } else {
            products = productRepository.findByDiemCanBangGreaterThan(diemCanBang, PageRequest.of(page, size));
        }
    }
    else if (productName == null && minPrice == null && maxPrice == null && diemCanBang != 0) {
        if (diemCanBang <= 285) {
            products = productRepository.findByBrandsIdAndDiemCanBangLessThan(brandId,diemCanBang, PageRequest.of(page, size));
        } else if (diemCanBang <= 295) {
            products = productRepository.findByBrandsIdAndDiemCanBangBetween(brandId ,285, 295, PageRequest.of(page, size));
        } else {
            products = productRepository.findByBrandsIdAndDiemCanBangGreaterThan(brandId,diemCanBang, PageRequest.of(page, size));
        }
    }
    else if (productName == null && brandId == null && diemCanBang != 0) {
        if (diemCanBang <= 285) {
            products = productRepository.findByDiemCanBangLessThanAndPriceBetween(diemCanBang,minPrice,maxPrice, PageRequest.of(page, size));
        } else if (diemCanBang <= 295) {
            products = productRepository.findByDiemCanBangBetweenAndPriceBetween(285, 295,minPrice ,maxPrice, PageRequest.of(page, size));
        } else {
            products = productRepository.findByDiemCanBangGreaterThanAndPriceBetween(diemCanBang,minPrice , maxPrice, PageRequest.of(page, size));
        }
    }
    else if (productName == null && diemCanBang != 0) {
        if (diemCanBang <= 285) {
            products = productRepository.findByBrandsIdAndDiemCanBangLessThanAndPriceBetween( brandId, diemCanBang,minPrice,maxPrice);
        } else if (diemCanBang <= 295) {
            products = productRepository.findByBrandsIdAndDiemCanBangBetweenAndPriceBetween(brandId,285, 295,minPrice ,maxPrice);
        } else {
            products = productRepository.findByBrandsIdAndDiemCanBangGreaterThanAndPriceBetween(brandId,diemCanBang,minPrice , maxPrice);
        }
    }
    // Tìm kiếm theo brand
    else if (productName == null && minPrice == null && maxPrice == null) {
        products = productRepository.findByBrandsId(brandId, PageRequest.of(page, size));
    }
    // Tìm kiếm theo tên
    else if (brandId == null && minPrice == null && maxPrice == null) {
        if (productName != null) {
            products = productRepository.findByNameContaining(productName, PageRequest.of(page, size));
        } else {
            products = productRepository.findAll(PageRequest.of(page, size)).getContent();
        }
    }
    // Tìm kiếm theo giá
    else if (productName == null && brandId == null) {
        products = productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    // Tìm kiếm theo brand + giá
    else if (productName == null) {
        products = productRepository.findByBrandsIdAndPriceBetween(brandId, minPrice, maxPrice);
    }
    // Trường hợp còn lại, trả về danh sách sản phẩm mặc định
    else {
        products = productRepository.findAll(PageRequest.of(page, size)).getContent();
    }
    return products;
}

public void sapXep(String sort , List<Product> products){

    // Sắp xếp danh sách sản phẩm nếu tham số sort được truyền vào từ frontend
    if (sort != null) {
        switch (sort) {
            case "id_asc":
                Collections.sort(products, Comparator.comparing(Product::getId));
                break;
            case "id_desc":
                Collections.sort(products, Comparator.comparing(Product::getId).reversed());
                break;
            case "price_asc":
                Collections.sort(products, Comparator.comparing(Product::getPrice));
                break;
            case "price_desc":
                Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
                break;
            case "name_asc":
                Collections.sort(products, Comparator.comparing(Product::getName));
                break;
            case "name_desc":
                Collections.sort(products, Comparator.comparing(Product::getName).reversed());
                break;
            default:
                // Không sắp xếp
                break;
        }
    }
}
}
