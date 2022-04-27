/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.repository.EmployeeRepository;
import com.gt.interpackage.administration.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author Luis
 */
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository _empRepository;
    
    @InjectMocks
    private EmployeeService _empService;
    
    private Employee emp;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        emp = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
    }
    
    @Test
    public void testFindAll(){
        //Falta
    }
    
    @Test
    public void testFindActivates() throws Exception {
        Mockito.when(
                _empRepository.getAllActivates())
                .thenReturn(Arrays.asList(emp));
        assertNotNull(_empService.findAllActivates());
        assertEquals(_empService.findAllActivates().size(), 1);
        
    }
    
    @Test
    public void testFindDeactivates(){
        Mockito.when(
                _empRepository.getAllDeactivates())
                .thenReturn(_empService.findAllDeactivates());
        assertNotNull(_empService.findAllDeactivates());
        assertNotEquals(_empService.findAllDeactivates().size(), 1);
    }
    
    @Test
    public void testFindAllActivatesNotAdmin(){
        Mockito.when(
                _empRepository.getAllActivatesNotAdmin())
                .thenReturn(_empService.findAllActivatesNotAdmin());
        assertNotNull(_empService.findAllActivatesNotAdmin());
        assertNotEquals(_empService.findAllActivatesNotAdmin().size(), 1);
    }
    
    @Test
    public void testSave(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        Employee empSaved = _empService.save(new Employee("Usuario", "Prueba", 1, "123453678", "prueba2@gmail.com", "userPrueba123", true));
        assertNotNull(empSaved);
        assertEquals(empSaved.getUsername(), "userPrueba123");
        Mockito.verify(_empRepository).save(ArgumentMatchers.any(Employee.class));
    }
    
    @Test
    public void testUpdatedSuccesfully() throws Exception {
        Employee updated = new Employee(555L, "Usuario", "Actualizado", 1, "12345678", "prueba@gmail.com", "userUpdated123", true);
        Mockito.when(
                _empRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(new Employee(555L, "Usuario", "Actualizado", 1, "12345678", "prueba@gmail.com", "userUpdated123", true));
        Employee empUpdated = _empService.update2(updated, 555L);
        assertNotNull(empUpdated);
        assertEquals(updated.getUsername(), empUpdated.getUsername());
        Mockito.verify(_empRepository).save(ArgumentMatchers.any(Employee.class));
        Mockito.verify(_empRepository).getById(ArgumentMatchers.any(Long.class));        
    }
    
    @Test 
    public void testCreateEmployee(){
        //Falta
    }
    
    @Test
    public void testExists() throws Exception {
        Mockito.when(
                _empRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        boolean exist = _empService.exists("josema12");
        assertNotNull(exist);
        assertEquals(exist, true);
        Mockito.verify(_empRepository).existsEmployeeByUsername(ArgumentMatchers.any(String.class));
    }
    
    @Test 
    public void testExistsByCUI() throws Exception{
        Mockito.when(
                _empRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        boolean exist = _empService.existsByCUI(12355L);
        assertNotNull(exist);
        assertEquals(exist, true);
        Mockito.verify(_empRepository).existsEmployeeByCUI(ArgumentMatchers.any(Long.class));
        
    }
    
    @Test
    public void getByCUI() throws Exception {
        Mockito.when(
                _empRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        Employee searched = _empService.getByCUI(12355L);
        assertNotNull(searched);
        assertEquals(searched.getUsername(), "josema12");
        Mockito.verify(_empRepository).getById(ArgumentMatchers.any(Long.class));
    }
    
    @Test
    public void testCreateEmployeeExists(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        ResponseEntity responseEntity = _empService.createEmployee(emp);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testCreateEmployeeSuccesfully(){
        ResponseEntity responseEntity = _empService.createEmployee(emp);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(_empRepository).save(ArgumentMatchers.any(Employee.class));
    }
}
