package com.example.doantn.repository;

import com.example.doantn.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    boolean existsByIdAndUserId(Long notificationId, Long userId);
}