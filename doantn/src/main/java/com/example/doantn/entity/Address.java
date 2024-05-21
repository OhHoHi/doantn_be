package com.example.doantn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;
    private String district;
    private String ward;
    private String street;
    private String fullName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address(String city, String district, String ward, String street, String houseNumber, User user) {
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.user = user;
    }


   // city (thành phố), district (quận/huyện), ward (phường/xã), street (tên đường/tòa nhà)
}