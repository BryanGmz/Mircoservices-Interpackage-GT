package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Package;
import com.gt.interpackage.operator.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    private Package packages;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        packages = new Package();
        packages.setId(1L);
    }

    @Test
    void getById() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.when(packageRepository.getById(packages.getId())).thenReturn(packages);
        assertEquals(packages.getId(), packageService.getById(packages.getId()).getId());
    }

    @Test
    void getByIdNotFound(){
        Exception exception = null;
        Mockito.when(packageRepository.getById(packages.getId())).thenThrow(new EntityNotFoundException());
        try{
            packageService.getById(packages.getId());
        } catch(Exception e){
            exception = e;
        }
        assertEquals("Paquete no encontrado en el sistema.", exception.getMessage());
    }

    @Test
    void update() throws Exception {
        Mockito.when(packageRepository.getById(packages.getId())).thenReturn(packages);
        Mockito.when(packageRepository.save(packages)).thenReturn(packages);
        assertEquals(packages.getId(), packageService.update(packages, packages.getId()).getId());
    }
}