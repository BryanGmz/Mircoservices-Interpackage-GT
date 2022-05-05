package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.model.Checkpoint;
import com.gt.interpackage.receptionist.model.Package;
import com.gt.interpackage.receptionist.model.PackageCheckpoint;
import com.gt.interpackage.receptionist.repository.PackageCheckpointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageCheckpointServiceTest {

    @Mock
    private PackageCheckpointRepository packageCheckpointRepository;

    @InjectMocks
    private PackageCheckpointService packageCheckpointService;

    private Checkpoint checkpoint;
    private Package pakages;
    private PackageCheckpoint packageCheckpoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pakages = new Package(1L, true, false, false, 100.0, 50.0, false, "Esta es una descripcion", null, 50.0, null, null);
        checkpoint = new Checkpoint(1L, "", 0D, 0, 0, true, null, null);
        packageCheckpoint = new PackageCheckpoint(checkpoint, pakages, new Time(10), true);
    }

    @Test
    public void testGetPackageCheckpoint() {
        Mockito.when(packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId())).thenReturn(packageCheckpoint);
        assertEquals(packageCheckpoint.getCheckpoint(), packageCheckpointService.getPackageCheckpoint(packageCheckpoint.getCheckpoint().getId()).getCheckpoint() );
    }

    @Test
    public void testGetTimeOnRouteByPackageId() {
        Mockito.when(packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId())).thenReturn(packageCheckpoint.getTimeOnCheckpoint().toString());
        assertEquals(packageCheckpoint.getTimeOnCheckpoint().toString(), packageCheckpointService.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId()).toString());
    }

    @Test
    public void testGetPackageCheckpointWithTimeOnRoute() {
        Mockito.when(packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId())).thenReturn(packageCheckpoint);
        Mockito.when(packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId())).thenReturn(packageCheckpoint.getTimeOnCheckpoint().toString());
        assertEquals(packageCheckpoint, packageCheckpointService.getPackageCheckpointWithTimeOnRoute(packageCheckpoint.getCheckpoint().getId()));
    }

    @Test
    public void testGetPackageCheckpointWithTimeOnRouteNull() {
        Mockito.when(packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId())).thenReturn(packageCheckpoint);
        Mockito.when(packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId())).thenReturn(null);
        assertEquals(new Time(00,00,00), packageCheckpointService.getPackageCheckpointWithTimeOnRoute(packageCheckpoint.getCheckpoint().getId()).getTimeOnCheckpoint());
    }
}