package com.gt.interpackage.authentification.service;

import com.gt.interpackage.authentification.dto.EmailValuesDTO;
import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author bryan
 */
@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmployeeService employeeService;

    // Metodo para enviar un correo para recuperar password
    public boolean sendEmailTemplate(EmailValuesDTO dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("username", dto.getUsername());
            model.put("url", Constants.URL_FRONTEND + "views/auth/reset-password/" + dto.getTokenPassword());
            context.setVariables(model);
            String htmlText = templateEngine.process("email-template", context);
            helper.setFrom(dto.getMailFrom());
            helper.setTo(dto.getMailTo());
            helper.setSubject(dto.getSubject());
            helper.setText(htmlText, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        } 
    }

    public ResponseEntity<?> sendEmail(EmailValuesDTO dto, String mailFrom) {
        try {
            dto.setMailFrom(mailFrom);
            dto.setSubject("Cambio de contraseña.");
            dto.setTokenPassword(UUID.randomUUID().toString());
            sendEmailTemplate(dto);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Se ha enviado un correo, para realizar el cambio de contraseña.");
            Employee employee = employeeService.getUserByUsernameOrEmail(dto.getMailTo());
            employee.setTokenPassword(dto.getTokenPassword());
            employeeService.save(employee);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 400 Bad Request
        }
    }

}
