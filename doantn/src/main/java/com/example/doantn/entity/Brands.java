package com.example.doantn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Brands")
public class Brands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brands_id")
    private Integer id;

    @Column(name = "brands_name")
    private String name;

    @Column(name = "description")
    private String description;

}
