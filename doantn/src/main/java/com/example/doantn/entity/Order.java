package com.example.doantn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private int status;

    @Column(name = "total_amount")
    private double totalAmount;

    public Order(User user, LocalDateTime orderDate, int status, double totalAmount) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
}
