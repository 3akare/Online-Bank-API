package com.onlinebanking.bank.service;

import com.onlinebanking.bank.dto.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    void sendEmailAlert(EmailDetailsDTO emailDetailsDTO, Context context, String template) throws MessagingException;
}
