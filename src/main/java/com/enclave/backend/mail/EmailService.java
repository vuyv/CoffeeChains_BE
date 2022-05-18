package com.enclave.backend.mail;


import com.enclave.backend.mail.properties.EmailConfigurationProperties;
import com.enclave.backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final EmailConfigurationProperties emailConfigurationProperties;
	private final EmployeeService employeeService;

//	@Async
//	public void sendEmail(String toMail, String subject, String messageBody) {
//		try {
//			log.info("Sending Email to {}", toMail);
//			MimeMessage message = javaMailSender.createMimeMessage();
//
//			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true, StandardCharsets.UTF_8.toString());
//			messageHelper.setSubject(subject);
//			messageHelper.setText(messageBody, true);
//			messageHelper.setFrom(emailConfigurationProperties.getUsername());
//				messageHelper.setTo(toMail);
//				javaMailSender.send(message);
//
//		} catch (MessagingException ex) {
//			log.error("Failed to send email to {}", toMail);
//		}
//	}

	@Async
	public void sendEmail(String toMail, String subject, String messageBody, File[] attachments) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true, StandardCharsets.UTF_8.toString());
			messageHelper.setSubject(subject);
			messageHelper.setText(messageBody, true);
			messageHelper.setFrom(emailConfigurationProperties.getUsername());
			for (File file : attachments) {
				FileSystemResource fr = new FileSystemResource(file);
				messageHelper.addAttachment(file.getName(), fr);
			}
				messageHelper.setTo(toMail);
				javaMailSender.send(message);
				log.info("Sending Daily Report to {}", toMail);

		} catch (MessagingException ex) {
			log.error("Failed to send daily report to {}", toMail);
		}
	}

}