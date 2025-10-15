package com.fg360.service.implementation;

import com.fg360.configuration.app.RabbitConfig;
import com.fg360.presentation.controller.dto.AlertDTO;
import com.fg360.service.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${email.sender}")
    private String emailSender;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @RabbitListener(queues = RabbitConfig.EMAIL_NOTIFICATION_QUEUE)
    public void sendEmail(AlertDTO alertDTO) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(emailSender);
            mailMessage.setTo(alertDTO.toUsers());
            mailMessage.setSubject(alertDTO.alertType());
            mailMessage.setText(alertDTO.generationDate() + "\n" + alertDTO.responsible() + " : " + alertDTO.generatingUnit());

            mailSender.send(mailMessage);
            logger.info("Recibido mensaje en la cola 'email_notification_queue': {}", alertDTO);

        } catch (MailException e) {
            logger.error("Failed to send email: {}", e.getMessage(), e);
        }
    }
}
