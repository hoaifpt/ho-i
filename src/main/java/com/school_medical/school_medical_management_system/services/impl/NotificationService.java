package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalAppointment;
import com.school_medical.school_medical_management_system.repositories.entites.Notification;
import com.school_medical.school_medical_management_system.repositories.impl.NotificationRepository;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.services.INotificationService;
import com.school_medical.school_medical_management_system.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotificationService implements INotificationService {

    private final JdbcTemplate jdbcTemplate;

    public NotificationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private IAppUserService appUserService;

    @Override
    public void sendConsentNotificationsForBatch(int batchId, String content, String type, String consentType) {
        notificationRepository.sendBatchNotifications(batchId, content, consentType);
    }

    @Override
    public void updateConsent(Long notificationId, Boolean consentStatus) {
        notificationRepository.updateConsentStatus(notificationId, consentStatus);
    }

    @Override
    public List<Notification> getNotificationsByParentId() {
        Integer parentUserId = appUserService.getUserByEmail(AuthUtils.getCurrentUserEmail()).getId();
        return notificationRepository.getNotificationsByParentId(parentUserId);
    }

    @Override
    public List<String> getEmailsByBatchId(int batchId) {
        String sql = """
        SELECT DISTINCT au.email
        FROM appuser au
        JOIN parent p ON au.user_id = p.user_id
        JOIN parentstudent ps ON ps.parent_user_id = au.user_id
        JOIN student s ON ps.student_id = s.student_id
        JOIN studentclass sc ON s.class_id = sc.class_id
        JOIN eventbatchclass ebc ON ebc.class_id = sc.class_id
        WHERE ebc.batch_id = ?
    """;

        return jdbcTemplate.queryForList(sql, new Object[]{batchId}, String.class);
    }

    // Thêm phương thức để lấy tất cả notification
    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.getAllNotifications();
    }

//    @Override
//    public void createNotificationOnAppointmentApproval(Long appointmentId) {
//        String sql = "SELECT * FROM medicalappointment WHERE appointment_id = ?";
//        MedicalAppointment appointment = jdbcTemplate.queryForObject(sql, new Object[]{appointmentId}, new BeanPropertyRowMapper<>(MedicalAppointment.class));
//
//        // Check if the appointment is approved
//        if (appointment != null && "Approved".equalsIgnoreCase(appointment.getStatus())) {
//            // Create a notification
//            Notification notification = new Notification();
//            notification.setContent("Your medical appointment has been approved.");
//            notification.setDateSent(new Timestamp(System.currentTimeMillis()));
//            notification.setConfirmed(false); // Assuming false initially
//            notification.setType("Medical Appointment");
//            notification.setStudentId(appointment.getStudentId());
//            notification.setParentUserId(appointment.);
//            notification.setConsentStatus(false); // Assuming consent status is false initially
//
//            // Save the notification
//            notificationRepository.saveNotification(notification);
//        }
//    }
//    }
}
