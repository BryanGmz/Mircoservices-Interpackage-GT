package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.*;
import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    private Package packages;
    private Destination destination;
    private Route route;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GT-Xela", "De Guate a Xela", 15.50);
        route = new Route(1L, "Ruta 1", 15, 35, true, destination);
        packages = new Package(1L, false, false, false, 0.0, 0.0, false, "j", new Invoice(), 0.1, route, destination);
    }

    @Test
    public void testGetById() {
        Mockito.when(packageRepository.getById(packages.getId())).thenReturn(packages);
        Package requestedPackage = packageService.getById(packages.getId());
        assertNotNull(requestedPackage);
        assertEquals(packages.getId(), requestedPackage.getId());
    }

    @Test
    public void testGetByIdNull() {
        Mockito.when(packageRepository.getById(packages.getId())).thenThrow(new EntityNotFoundException());
        Package requestedPackage = packageService.getById(packages.getId());
        assertNull(requestedPackage);
    }


    @Test
    public void testUpdate() throws Exception {
        Mockito.when(packageRepository.getById(packages.getId())).thenReturn(packages);
        Mockito.when(packageRepository.save(packages)).thenReturn(packages);
        Package requestedPackage = packageService.update(packages, packages.getId());
        assertNotNull(requestedPackage);
        assertEquals(packages.getId(), requestedPackage.getId());
    }
}