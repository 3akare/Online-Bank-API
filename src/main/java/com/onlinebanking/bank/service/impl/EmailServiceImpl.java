package com.onlinebanking.bank.service.impl;

import com.onlinebanking.bank.dto.EmailDetailsDTO;
import com.onlinebanking.bank.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

@Slf4j
@Service
@Async
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("$spring.mail.enabled}")
    private String enableEmail;

    @Override
    public void sendEmailAlert(EmailDetailsDTO emailDetailsDTO, Context context, String template) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        String htmlContent = templateEngine.process(template, context);

        if (Objects.equals(enableEmail, "true")){
            try{
                mimeMessageHelper.setFrom(senderEmail);
                mimeMessageHelper.setTo(emailDetailsDTO.recipient());
                mimeMessageHelper.setSubject(emailDetailsDTO.subject());
                mimeMessageHelper.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);
                System.out.println("Mail Sent!");
            }
            catch (MailException error){
                throw new RuntimeException("Mail service failed " + error);
            }
        }
        else {
            log.info("===== Email Disabled =====");
        }
    }
}
