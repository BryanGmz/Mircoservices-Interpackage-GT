/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.model.Package;
import com.gt.interpackage.receptionist.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
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
    public void testGetById() throws Exception {
        Mockito.when(_packageRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(pack);
        Package searched = _packageService.getById(1L);
        Assertions.assertThat(searched.getId()).isEqualTo(1L);
    }
    
    @Test
    public void testAddPackage(){
        Mockito.when(_packageRepository.save(ArgumentMatchers.any(Package.class)))
                .thenReturn(pack);
        Package packSaved = _packageService.addPackage(new Package(true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null));
        assertNotNull(packSaved);
        assertEquals(packSaved.getId(), 1L);
        Mockito.verify(_packageRepository).save(ArgumentMatchers.any(Package.class));
    }
    
    @Test
    public void testUpdatedSuccesfully() throws Exception {
        Package updated = new Package(12L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion para update", null, 50.0, null, null); 
        Mockito.when(
                _packageRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(pack);
        Mockito.when(
                _packageRepository.save(ArgumentMatchers.any(Package.class)))
                .thenReturn(new Package(12L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion para update", null, 50.0, null, null));
        Package packUpdated = _packageService.update(updated, 1L);
        assertNotNull(packUpdated);
        assertEquals(updated.getWeight(), packUpdated.getWeight());
        Mockito.verify(_packageRepository).save(ArgumentMatchers.any(Package.class));
        Mockito.verify(_packageRepository).getById(ArgumentMatchers.any(Long.class));
    } 
    
    @Test
    public void testGetPackagesByInvoice(){
        Mockito.when(_packageRepository.getPackagesByInvoice(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(pack));
        assertNotNull(_packageService.getPackagesByInvoice(1L));
    }

    @Test
    public void testGetPackagesOnRouteByInoviceId(){
        Mockito.doReturn(Arrays.asList(pack)).when(_packageRepository).findAllByInvoiceIdAndOnWayTrue(1L);
        List<Package> list = _packageService.getPackagesOnRouteByInoviceId(1L);
        assertEquals(1, list.size());
        assertEquals(pack.getId(), list.get(0).getId());

    }
    
    
    
}
