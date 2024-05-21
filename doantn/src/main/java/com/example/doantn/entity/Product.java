package com.example.doantn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "image_urls")
    @ElementCollection
    private List<String> imageUrls;

    @Column(name = "price")
    private Long price;

//    @Column(name = "brands_id")
//    private Integer brandsId;

    @Column(name = "status")
    private String status;

    @Column(name = "color")
    private String color;

    @Column(name = "chat_lieu_khung_vot")
    private String chatLieuKhungVot;

    @Column(name = "chat_lieu_than_vot")
    private String chatLieuThanVot;

    @Column(name = "trong_luong")
    private String trongLuong;

    @Column(name = "do_cung")
    private String doCung;

    @Column(name = "diem_can_bang")
    private int diemCanBang;

    @Column(name = "chieu_dai_vot")
    private String chieuDaiVot;

    @Column(name = "muc_cang_toi_da")
    private String mucCangToiDa;

    @Column(name = "chu_vi_can_cam")
    private String chuViCanCam;

    @Column(name = "trinh_do_choi")
    private String trinhDoChoi;

    @Column(name = "noi_dung_choi")
    private String noiDungChoi;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brands_id")
    private Brands brands;
}
