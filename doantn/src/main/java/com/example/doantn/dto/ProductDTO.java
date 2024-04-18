package com.example.doantn.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private String description;
    private List<MultipartFile> images;
    private long price;
    private String brandsName; // Sử dụng ID của thương hiệu thay vì tên
    private String status;
    private String color;
    private String chatLieuKhungVot;
    private String chatLieuThanVot;
    private String trongLuong;
    private String doCung;
    private String diemCanBang;
    private String chieuDaiVot;
    private String mucCangToiDa;
    private String chuViCanCam;
    private String trinhDoChoi;
    private String noiDungChoi;
}
