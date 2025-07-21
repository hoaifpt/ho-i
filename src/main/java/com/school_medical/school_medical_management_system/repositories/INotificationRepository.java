package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Notification;
import java.util.List;

public interface INotificationRepository {
    void sendBatchNotifications(int batchId, String content, String consentType);
    void updateConsentStatus(Long notificationId, Boolean consentStatus);
    List<Notification> getNotificationsByParentId(Integer parentUserId);
    List<Notification> getAllNotifications();
    void saveNotification(Notification notification);
}
