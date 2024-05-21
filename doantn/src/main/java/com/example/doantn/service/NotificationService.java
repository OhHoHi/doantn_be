package com.example.doantn.service;

import com.example.doantn.entity.Notification;
import com.example.doantn.entity.User;
import com.example.doantn.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void createNotification(String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    public boolean existsById(Long notificationId) {
        return notificationRepository.existsById(notificationId);
    }

    public boolean belongsToUser(Long notificationId, Long userId) {
        return notificationRepository.existsByIdAndUserId(notificationId, userId);
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}