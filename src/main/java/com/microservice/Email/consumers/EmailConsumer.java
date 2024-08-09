package com.microservice.Email.consumers;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microservice.Email.dtos.EmailDto;
import com.microservice.Email.models.Email;
import com.microservice.Email.services.EmailService;

@Component
public class EmailConsumer {
	
	@Autowired
	private EmailService emailService;
	
	@RabbitListener(queues = "${broker.queue.email.name}")
	public void listenEmailQueue (@Payload EmailDto emailDto) {
		Email email = new Email();
		email.setEmailTo(emailDto.emailTo());
		email.setUserId(emailDto.id());
		email.setSubject(emailDto.subject());
		email.setSendEmailDate(LocalDateTime.now());
		email.setMessage(emailDto.text());
		
		emailService.sendEmail(email);
	}

}
