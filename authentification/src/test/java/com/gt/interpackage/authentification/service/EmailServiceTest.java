package com.gt.interpackage.authentification.service;

import com.gt.interpackage.authentification.dto.EmailValuesDTO;
import com.gt.interpackage.authentification.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmailService mockEmailService;

    private EmailValuesDTO emailValuesDTO;
    private Employee employee;

    private static final String HTML = "<!DOCTYPE html>" +
            "\n<html lang=\"en\">" +
            "\n<head>" +
            "\n<meta charset=\"UTF-8\">" +
            "\n<title>Title</title> " +
            "\n</head>" +
            "\n<body> " +
            "\n</body> " +
            "\n</html>";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        javaMailSender = Mockito.mock(JavaMailSender.class);
        templateEngine = Mockito.mock(TemplateEngine.class);
        emailValuesDTO = new EmailValuesDTO("brgg13@gmail.com", "brgg13@gmail.com", "Mensaje Email", "prueba123", "12345678");
        employee = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
    }

    @Test
    public void testSendEmailTemplate() {
        Mockito.when(
                javaMailSender.createMimeMessage())
                .thenReturn(new MimeMessage((Session) null));

        /*Mockito.when(
                templateEngine.process("prueba", ArgumentMatchers.any(Context.class)))
                .thenReturn(HTML);
        Assertions.assertTrue(emailService.sendEmailTemplate(emailValuesDTO));
        */
    }

    @Test
    public void testSendEmail() throws Exception {
        Mockito.when(
                mockEmailService.sendEmailTemplate(ArgumentMatchers.any(EmailValuesDTO.class))
        ).thenReturn(true);
        Mockito.when(
                employeeService.getUserByUsernameOrEmail(ArgumentMatchers.any(String.class))
        ).thenReturn(employee);
        Mockito.when(
                employeeService.save(ArgumentMatchers.any(Employee.class))
        ).thenReturn(employee);
        ResponseEntity responseEntity = emailService.sendEmail(emailValuesDTO, "brgg13@gmail.com");
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
