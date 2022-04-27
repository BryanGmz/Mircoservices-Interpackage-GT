/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.authentification;

import com.gt.interpackage.authentification.model.Auth;
import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.repository.AuthRepository;
import com.gt.interpackage.authentification.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
/**
 *
 * @author Luis
 */
public class TestAuthService {
    
    @Mock
    private AuthRepository _authRepository;
    
    @InjectMocks
    private AuthService _authService;
    
    private Auth auth;
    private Employee emp;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        emp = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
    }
    
    @Test
    public void testLoginUserNoExist() throws Exception{
        ResponseEntity response = _authService.login("usuario", "12333");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(_authRepository).findByUsernameAndPasswordAndActivoTrue("usuario", "12333");
    }
 
    @Test
    public void testLoginSucesfully() throws Exception{
        Mockito.when(_authRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        ResponseEntity response = _authService.login("josema12", "12345678");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Mockito.verify(_authRepository).findByUsernameAndPasswordAndActivoTrue("josema12", "12345678");
    }
    
}
