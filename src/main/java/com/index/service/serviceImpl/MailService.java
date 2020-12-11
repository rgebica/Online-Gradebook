package com.index.service.serviceImpl;

import com.index.exceptions.SpringGradebookException;
import com.index.dto.NotificationEmail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public
class MailService {

    JavaMailSender mailSender;
    MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("gradebook@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringGradebookException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

        public void sendResponse(NotificationEmail notificationEmail){
            MimeMessagePreparator responsePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom("gradebook@email.com");
                messageHelper.setTo(notificationEmail.getRecipient());
                messageHelper.setSubject(notificationEmail.getSubject());
                messageHelper.setText(notificationEmail.getBody());
            };
            try {
                mailSender.send(responsePreparator);
                log.info("Activation email sent!!");
            } catch (MailException e) {
                log.error("Exception occurred when sending mail", e);
                throw new SpringGradebookException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
            }
        }
    }
