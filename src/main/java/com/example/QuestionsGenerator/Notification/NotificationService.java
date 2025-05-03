package com.example.QuestionsGenerator.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    public void sendNotification(String message, NotificationType notificationType) {
        System.out.println("\n===== Generated Message To Send =====\n");
        System.out.println(message); // ✅ Print the full message content nicely
        System.out.println("\n=====================================\n");

        // ❌ Commenting out actual sending logic for now to save WhatsApp credits
        /*
        switch (notificationType) {
            case EMAIL:
                emailService.sendEmail("recipient-email@example.com", "Today's Questions", message);
                break;
            case WHATSAPP:
                WhatsAppSender.sendWhatsAppMessage(message);
                break;
            default:
                throw new UnsupportedOperationException("Notification type not supported: " + notificationType);
        }
        */
    }
}
