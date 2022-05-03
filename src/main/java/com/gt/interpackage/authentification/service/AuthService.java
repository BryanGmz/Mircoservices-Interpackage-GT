/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.authentification.service;

import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.repository.AuthRepository;
import com.gt.interpackage.authentification.utils.JWTUtil;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.interpackage.authentification.model.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author Luis
 */
@Service
public class AuthService {

    @Autowired
    private AuthRepository _authRepository;
    
    @Autowired
    private JWTUtil _jwtUtil;
    
    public ResponseEntity<Auth> login(String username, String pass) throws Exception {
        Employee emp2 = _authRepository.findByUsernameAndPasswordAndActivoTrue(username, pass);
        if(emp2 != null){
            String tokenJwt = _jwtUtil.create(String.valueOf(emp2.getCUI()), emp2.getUsername());
            Auth authUser = new Auth(tokenJwt, emp2);
            return ResponseEntity.ok(authUser);
        }
        return new ResponseEntity("Error de credenciales, porfavor intente otra vez", HttpStatus.BAD_REQUEST);
    }
}
