package com.example.doantn.controller;

import com.example.doantn.entity.Notification;
import com.example.doantn.service.NotificationService;
import com.example.doantn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;


    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }




    @DeleteMapping("/{notificationId}/user/{userId}")
    public ResponseEntity<String> deleteNotificationForUser(@PathVariable Long notificationId, @PathVariable Long userId) {
        // Kiểm tra xem thông báo có tồn tại không
        if (!notificationService.existsById(notificationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thông báo");
        }

        // Kiểm tra xem người dùng có tồn tại không
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
        }

        // Kiểm tra xem thông báo có thuộc về người dùng không
        if (!notificationService.belongsToUser(notificationId, userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông báo không thuộc về người dùng");
        }

        // Xóa thông báo
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("Thông báo đã được xóa");
    }
}