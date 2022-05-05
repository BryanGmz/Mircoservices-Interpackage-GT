package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.*;
import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.repository.PackageCheckpointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

class PackageCheckpointServiceTest {

    @Mock
    private PackageCheckpointRepository packageCheckpointRepository;

    @InjectMocks
    private PackageCheckpointService packageCheckpointService;

    private PackageCheckpoint packageCheckpoint;
    private Checkpoint checkpoint;
    private Package packages;
    private Employee employee;
    private EmployeeType employeeType;
    private Destination destination;
    private Route route;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeType = new EmployeeType(2L, "operator", "Operador");
        employee = new Employee(1234678L, "Juan", "Gonzales", "juanito", "juanito@gmail.com", 2, null, "12345678", true);
        destination = new Destination(1L, "GT-Xela", "De Guate a Xela", 15.50);
        route = new Route(1L, "Ruta 1", 15, 35, true, destination);
        checkpoint = new Checkpoint(1L, "Punto de control 1", 15.50, 25, 12, true, employee, route);
        packages = new Package(1L, false, false, false, 0.0, 0.0, false, "j", new Invoice(), 0.1, route, destination);
        packageCheckpoint = new PackageCheckpoint(checkpoint, packages, null, true);
    }

    @Test
    public void existsAnyRegisterOfCheckpointById() {
        Mockito.doReturn(true).when(packageCheckpointRepository).existsPackageCheckpointByCheckpointId(1L);
        assertTrue(packageCheckpointService.existsAnyRegisterOfCheckpointById(1L));

    }

    @Test
    public void create() {
        packageCheckpointService.create(packageCheckpoint);
    }
}