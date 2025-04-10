package com.example.QuestionsGenerator.WhatsApp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppSender {

    // Replace with your actual Twilio credentials
    public static final String ACCOUNT_SID = "ACddc7abcef1e35e20d2086a5231e1927d";
    public static final String AUTH_TOKEN = "de28b7fa686267a686fba0aad3648e29";
    public static final String FROM_NUMBER = "whatsapp:+14155238886"; // Twilio sandbox number
    public static final String TO_NUMBER = "whatsapp:+918056734634"; // Your verified number (no space)

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendWhatsAppMessage(String body) {
        Message message = Message.creator(
                new PhoneNumber(TO_NUMBER),   // TO
                new PhoneNumber(FROM_NUMBER), // FROM
                body                          // Message body
        ).create();

        System.out.println("Message sent! SID: " + message.getSid());
    }
}
