package com.example.doantn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String sentDateTime; // Thêm trường cho ngày và thời gian gửi thông báo

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}