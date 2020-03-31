package com.movie.onlinestore.service;

import com.movie.onlinestore.model.Order;
import com.movie.onlinestore.model.OrderItem;
import com.movie.onlinestore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void sendInvoice(User user, Order order) {
        executorService.execute(() -> {
            try {
                sendEmailInvoice(user, order);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendEmailInvoice(User user, Order order) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());


        helper.setTo(user.getEmail());
        helper.setSubject("Order Invoice " + order.getOrderDate());

        StringBuilder orderItemDetails = new StringBuilder();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDetails.append(String.format("%30s - $%s", orderItem.getMovie().getTitle(), orderItem.getTotalCost()));
            orderItemDetails.append("<br>");
        }

        Context context = new Context();
        String html = springTemplateEngine.process("email-invoice-template", context);

        String outputHTML = String.format(html, user.getName(), orderItemDetails, order.getTotalCost());

        helper.setText(outputHTML, true);

        emailSender.send(message);
    }

}
