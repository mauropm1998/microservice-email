package com.microservice.Email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.microservice.Email.enums.EmailStatus;
import com.microservice.Email.models.Email;
import com.microservice.Email.repositories.EmailRepository;

import jakarta.transaction.Transactional;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmailRepository emailRepository;

	@Value("${spring.mail.username}")
	String emailFrom;

	@Transactional
	public Email sendEmail(Email email) {
		email.setEmailFrom(emailFrom);
		email.setSendEmailDate(LocalDateTime.now());

		try {
			SimpleMailMessage simpleMessage = new SimpleMailMessage();
			simpleMessage.setFrom(emailFrom);
			simpleMessage.setSubject(email.getSubject());
			simpleMessage.setTo(email.getEmailTo());
			simpleMessage.setText(email.getMessage());

			mailSender.send(simpleMessage);

			email.setStatus(EmailStatus.SENT);
		} catch (MailException e) {
			email.setStatus(EmailStatus.FAILED);
		} finally {			
			return emailRepository.save(email);
		}
	}

}
