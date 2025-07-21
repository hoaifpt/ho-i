package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ConsentResponse;
import com.school_medical.school_medical_management_system.models.NotificationRequest;
import com.school_medical.school_medical_management_system.repositories.INotificationRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Notification;
import com.school_medical.school_medical_management_system.repositories.impl.UserRepositoryImpl;
import com.school_medical.school_medical_management_system.services.IEmailService;
import com.school_medical.school_medical_management_system.services.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-batch/{batchId}")
    public ResponseEntity<String> sendBatchNotifications(@PathVariable int batchId, @RequestBody NotificationRequest request) {
        try {
            notificationService.sendConsentNotificationsForBatch(batchId, request.getContent(), request.getType(), request.getConsentType());
            return ResponseEntity.ok("Notifications sent successfully for batch ID: " + batchId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send notifications: " + e.getMessage());
        }
    }

    @PostMapping("/consent/{notificationId}")
    public ResponseEntity<String> updateConsent(@PathVariable Long notificationId,
                                                @RequestBody ConsentResponse response) {
        try {
            notificationService.updateConsent(notificationId, response.getConsentStatus());
            return ResponseEntity.ok("Consent updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update consent: " + e.getMessage());
        }
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Notification>> getNotificationsByParent() {
        List<Notification> notifications = notificationService.getNotificationsByParentId();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationService.getAllNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Lỗi hệ thống khi lấy tất cả thông báo
        }
    }

    @PostMapping("/send")
    public String sendNotificationToAllUsers(@RequestParam String subject, @RequestParam String content) {
        List<String> emails = userRepository.getAllUserEmails();

        for (String email : emails) {
            emailService.sendEmail(email, subject, content);
        }

        return "Notification sent to all users.";
    }

    @PostMapping("/send-by-batch/{batchId}")
    public ResponseEntity<String> sendByBatch(@PathVariable int batchId,
                                              @RequestParam String subject,
                                              @RequestParam String content) {
        List<String> emails = notificationService.getEmailsByBatchId(batchId);

        for (String email : emails) {
            emailService.sendEmail(email, subject, content); // async
        }

        return ResponseEntity.ok("Đã gửi email cho các phụ huynh trong batch " + batchId);
    }

    @PostMapping("/sendone")
    public String sendNotificationToUser(@RequestParam String email,@RequestParam String subject, @RequestParam String content) {
            emailService.sendEmail(email, subject, content);
        return "Notification sent to this user";
    }
}
