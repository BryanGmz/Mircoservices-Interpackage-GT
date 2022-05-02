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
                .thenReturn(Arrays.asList(emp));
        assertNotNull(_empService.findAllDeactivates());
        assertEquals(_empService.findAllDeactivates().size(), 1);
    }
    
    @Test
    public void testFindAllActivatesNotAdmin(){
        Mockito.when(
                _empRepository.getAllActivatesNotAdmin())
                .thenReturn(Arrays.asList(emp));
        assertNotNull(_empService.findAllActivatesNotAdmin());
        assertEquals(_empService.findAllActivatesNotAdmin().size(), 1);
    }
    
    @Test
    public void testSave(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        Employee empSaved = _empService.save(new Employee("Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true));
        assertNotNull(empSaved);
        assertEquals(empSaved.getUsername(), "josema12");
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
    public void testUpdatedNotFoundEmployee() throws Exception {
        Employee updated = new Employee(555L, "Usuario", "Actualizado", 1, "12345678", "prueba@gmail.com", "userUpdated123", true);
        Mockito.when(
                _empRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(new Employee(555L, "Usuario", "Actualizado", 1, "12345678", "prueba@gmail.com", "userUpdated123", true));
        Employee empUpdated = _empService.update2(updated, 555L);
        assertNull(empUpdated);
        assertEquals(empUpdated, null);
    }
    
    @Test
    public void testMethodUpdate() throws Exception {
        Employee updated = new Employee("Luis Fernando", "Marquez", 1, "12345678", "prueba@gmail.com", "josema12", true);
        Mockito.when(
                _empService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(emp);
        ResponseEntity response = _empService.update(updated, 555L);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
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
        Mockito.when(
                _empRepository.existsEmployeeByUsername(ArgumentMatchers.any(String.class)))
                .thenReturn(true);
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
        Mockito.when(
                _empRepository.existsEmployeeByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
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
    public void testCreateEmployeeCUIExists(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        Mockito.when(
                _empService.existsByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        ResponseEntity responseEntity = _empService.createEmployee(emp);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void testCreateEmployeeUsernameExists(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        Mockito.when(
                _empService.exists(ArgumentMatchers.any(String.class)))
                .thenReturn(true);
        ResponseEntity response = _empService.createEmployee(emp);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void testCreateEmployeeSuccesfully(){
        Mockito.when(
                _empRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(emp);
        ResponseEntity responseEntity = _empService.createEmployee(emp);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(_empRepository).save(ArgumentMatchers.any(Employee.class));
    }
}
