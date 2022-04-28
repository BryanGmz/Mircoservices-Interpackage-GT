/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.authentification.controller;

import com.gt.interpackage.authentification.service.AuthService;
import com.gt.interpackage.authentification.source.Constants;
import com.gt.interpackage.authentification.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gt.interpackage.authentification.model.Auth;
import com.gt.interpackage.authentification.model.Employee;

/**
 *
 * @author Luis
 */

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_AUTH + "/login")
public class AuthController {
    
    @Autowired
    private AuthService _authService;

    @Autowired
    private JWTUtil _jwtUtil;
    
    @PostMapping("/")
    public ResponseEntity<Auth> login(@RequestBody Employee empRequest){
        try {
            return _authService.login(empRequest.getUsername(), empRequest.getPassword());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
