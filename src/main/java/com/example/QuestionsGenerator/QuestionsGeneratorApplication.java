package com.example.QuestionsGenerator;

import com.example.QuestionsGenerator.WhatsApp.WhatsAppSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestionsGeneratorApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuestionsGeneratorApplication.class, args);
	//	WhatsAppSender.sendWhatsAppMessage("📢 Hello! This is a test message from Twilio WhatsApp using Spring Boot.");

	}

}
