/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.services;

import com.gt.interpackage.reports.model.Package;
import com.gt.interpackage.reports.service.PackageService;
import com.gt.interpackage.reports.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Luis
 */
public class PackageServiceTest {
    
    @Mock
    private PackageRepository _packageRepository;
    
    @InjectMocks
    private PackageService _packageService;

    private Package pack;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        pack = new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null);       
    }

    @Test
    public void testGetPackagesByInvoice(){
        Mockito.when(_packageRepository.getPackagesByInvoice(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(pack));
        assertNotNull(_packageService.getPackagesByInvoice(1L));
    }
}
