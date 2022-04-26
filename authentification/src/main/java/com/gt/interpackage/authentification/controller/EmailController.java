package com.gt.interpackage.authentification.controller;

import com.gt.interpackage.authentification.dto.EmailValuesDTO;
import com.gt.interpackage.authentification.service.EmailService;
import com.gt.interpackage.authentification.service.EmployeeService;
import com.gt.interpackage.authentification.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_AUTH + "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeService employeeService;

    @Value ("${spring.mail.username}")
    private String mailFrom;

    @PostMapping("/send-email-forgot-password")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
       return emailService.sendEmail(dto, mailFrom);
    }
}
