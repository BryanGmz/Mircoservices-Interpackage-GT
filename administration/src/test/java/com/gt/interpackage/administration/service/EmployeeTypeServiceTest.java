package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.EmployeeType;
import com.gt.interpackage.administration.repository.EmployeeTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTypeServiceTest {

    @Mock
    private EmployeeTypeRepository employeeTypeRepository;

    @InjectMocks
    private EmployeeTypeService employeeTypeService;

    private EmployeeType employeeType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeType = new EmployeeType(1L,"administrator", "Administrador");
    }

    @Test
    public void testGetEmployeeTypeByName() {
        Mockito.when(employeeTypeRepository.findByName(employeeType.getName())).thenReturn(Optional.of(employeeType));
        EmployeeType requestEmployeeType = employeeTypeService.getEmployeeTypeByName(employeeType.getName());
        assertEquals(employeeType.getId(), requestEmployeeType.getId());
    }

    @Test
    public void testCreate() {
        Mockito.doReturn(employeeType).when(employeeTypeRepository).save(employeeType);
        EmployeeType savedEmployeeType = employeeTypeService.create(employeeType);
        assertEquals(employeeType.getId(), savedEmployeeType.getId());
    }

    @Test
    public void testDeleteAllTypes() {
        Mockito.doReturn(0L).when(employeeTypeRepository).count();
        employeeTypeService.deleteAllTypes();
    }

}