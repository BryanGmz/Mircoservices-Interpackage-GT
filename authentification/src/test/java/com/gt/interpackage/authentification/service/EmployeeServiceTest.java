package com.gt.interpackage.authentification.service;

import com.gt.interpackage.authentification.dto.ChangePasswordDTO;
import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private ChangePasswordDTO changePasswordDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employee = new Employee(12355L, "Jose Manuel", "Garcia", 1, "12345678", "prueba@gmail.com", "josema12", true);
        changePasswordDTO = new ChangePasswordDTO("12345678", "12345678", "askldfjn132mnxzbncklvjbwekbm");
    }

    @Test
    public void testSaveEmployee() {
        Mockito.when(
                        employeeRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);
        Employee employeeSaved = employeeService.save(employee);
        assertNotNull(employeeSaved);
        assertEquals(employee.getCUI(), employeeSaved.getCUI());
        Mockito.verify(employeeRepository).save(ArgumentMatchers.any(Employee.class));
    }

    @Test
    public void testGetUserByUsernameOrEmail() throws Exception {
        Mockito.when(
                employeeRepository.getByUsernameOrEmail(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(employee);
        Employee searched = employeeService.getUserByUsernameOrEmail("prueba@gmail.com");
        assertNotNull(searched);
        assertEquals(employee.getEmail(), "prueba@gmail.com");
        Mockito.verify(employeeRepository).getByUsernameOrEmail(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class));
    }

    @Test
    public void testGetUserByUsernameOrEmailButEntityNotFoundException() throws Exception {
        Mockito.when(
                        employeeRepository.getByUsernameOrEmail(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenThrow(new EntityNotFoundException());
        assertNull(employeeService.getUserByUsernameOrEmail("Juaniton"));
        Mockito.verify(employeeRepository).getByUsernameOrEmail(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class));
    }

    @Test
    public void testGetUserByTokenPassword() throws Exception {
        Mockito.when(
                employeeRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenReturn(employee);
        Employee searched = employeeService.getUserByTokenPassword("1234567983532132a");
        assertNotNull(searched);
        assertEquals(searched.getCUI(), 12355L);
        Mockito.verify(employeeRepository).findByTokenPassword(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testGetUserByTokenPasswordButEntityNotFoundException() throws Exception {
        Mockito.when(
                employeeRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenThrow(new EntityNotFoundException());
        assertNull(employeeService.getUserByTokenPassword("12345678"));
        Mockito.verify(employeeRepository).findByTokenPassword(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testChangePasswordButNotIsValidateFields() {
        ResponseEntity responseEntity = employeeService.changePassword(new ChangePasswordDTO());
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testChangePasswordButPasswordDoNotMath() {
        changePasswordDTO.setConfirmPassword("111123232");
        ResponseEntity responseEntity = employeeService.changePassword(changePasswordDTO);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testChangePasswordButCantNotFindAnEmployeeWithTheToken() {
        Mockito.when(
                employeeRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenReturn(null);
        ResponseEntity responseEntity = employeeService.changePassword(changePasswordDTO);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Mockito.verify(employeeRepository).findByTokenPassword(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testChangePasswordButException() {
        Mockito.when(
                        employeeRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenThrow(new RuntimeException());
        ResponseEntity responseEntity = employeeService.changePassword(changePasswordDTO);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.verify(employeeRepository).findByTokenPassword(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testChangePassword() {
        Mockito.when(
                        employeeRepository.findByTokenPassword(ArgumentMatchers.any(String.class)))
                .thenReturn(employee);
        Mockito.when(
                        employeeRepository.save(ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);
        ResponseEntity responseEntity = employeeService.changePassword(changePasswordDTO);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Mockito.verify(employeeRepository).findByTokenPassword(ArgumentMatchers.any(String.class));
        Mockito.verify(employeeRepository).save(ArgumentMatchers.any(Employee.class));
    }
}
