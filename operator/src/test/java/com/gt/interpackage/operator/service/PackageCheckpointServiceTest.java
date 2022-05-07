package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Checkpoint;
import com.gt.interpackage.operator.model.Package;
import com.gt.interpackage.operator.model.PackageCheckpoint;
import com.gt.interpackage.operator.model.Route;
import com.gt.interpackage.operator.repository.PackageCheckpointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PackageCheckpointServiceTest {

    @Mock
    private PackageCheckpointRepository packageCheckpointRepository;

    @Mock
    private CheckpointService checkpointService;

    @Mock
    private PackageService packageService;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private PackageCheckpointService packageCheckpointService;

    private PackageCheckpoint packageCheckpoint;
    private Package packages;
    private Checkpoint checkpoint;
    private Route route;
    private Exception exception;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        route = new Route(1L, "", 0, 0, true, null);
        checkpoint = new Checkpoint(1L, "", 0D, 10, 8, true, null, route);
        packages = new Package(1L, true, false, false, 0D, 0D, false, "", null, 0D, route, null);
        packageCheckpoint = new PackageCheckpoint(checkpoint, packages, new Time(0),true);
        packageCheckpoint.setDate(new Date(10));
    }

    @Test
    void getAllPackageCheckpointOnCheckpoint() {
        Mockito.when(
            packageCheckpointRepository.findAllByCheckpointIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId()))
        .thenReturn(
            List.of(packageCheckpoint)
        )
        ;
        assertEquals(
    1,
            packageCheckpointService.getAllPackageCheckpointOnCheckpoint(packageCheckpoint.getCheckpoint().getId()).size()
        );

        assertEquals(
            packageCheckpoint.getCheckpoint().getId(),
            packageCheckpointService.getAllPackageCheckpointOnCheckpoint(packageCheckpoint.getCheckpoint().getId()).get(0).getCheckpoint().getId()
        );

    }

    @Test
    void getPackageCheckpoint(){
        Mockito.when(
            packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId()))
        .thenReturn(
            packageCheckpoint
        );

        assertNotNull(packageCheckpointService.getPackageCheckpoint(packageCheckpoint.getCheckpoint().getId()));

        assertEquals(
            packageCheckpoint.getCheckpoint(),
            packageCheckpointService.getPackageCheckpoint(packageCheckpoint.getCheckpoint().getId()).getCheckpoint()
        );
    }

    @Test
    void getTimeOnRouteByPackageId() {
        Mockito.when(
            packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId()))
        .thenReturn(
            "00:00:00"
        );
        assertEquals(
    "00:00:00",
            packageCheckpointService.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId())
        );
    }

    @Test
    void getByCheckpointIdPackageId(){
        Mockito.when(
            packageCheckpointRepository.findByCheckpointIdAndPackagesId(
                    packageCheckpoint.getCheckpoint().getId(),
                    packageCheckpoint.getPackages().getId()
            )
        ).thenReturn(packageCheckpoint);

        assertEquals(
            packageCheckpoint.getCheckpoint(),
            packageCheckpointService.getByCheckpointIdPackageId(
                packageCheckpoint.getCheckpoint().getId(),
                packageCheckpoint.getPackages().getId()
            ).getCheckpoint());

        assertEquals(
                packageCheckpoint.getPackages(),
                packageCheckpointService.getByCheckpointIdPackageId(
                        packageCheckpoint.getCheckpoint().getId(),
                        packageCheckpoint.getPackages().getId()
                ).getPackages());
    }

    @Test
    void getNextCheckpointId(){
        Mockito.when(packageCheckpointRepository.getNextCheckpointId(0L,0L)).thenReturn(10L);
        assertEquals(10L, packageCheckpointService.getNextCheckpointId(0L, 0L));
    }

    @Test
    void create() {
        packageCheckpointService.create(packageCheckpoint);
    }


    @Test
    void update(){
        packageCheckpointService.update(packageCheckpoint);
    }

    @Test
    void getPackageCheckpointWithTimeOnRoute() {
        Mockito.when(
            packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId()))
        .thenReturn(
            packageCheckpoint
        );

        Mockito.when(
            packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId()))
        .thenReturn(
            "20:10:00"
        );

        PackageCheckpoint testPackageCheckpoint = packageCheckpointService.getPackageCheckpointWithTimeOnRoute(packageCheckpoint.getCheckpoint().getId());
        assertEquals(
                "20:10:00",
                testPackageCheckpoint.getTimeOnCheckpoint().toString()
        );
    }

    @Test
    void getPackageCheckpointWithTimeOnRouteNullTime() {
        Mockito.when(
                        packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(packageCheckpoint.getCheckpoint().getId()))
                .thenReturn(
                        packageCheckpoint
                );

        Mockito.when(
                        packageCheckpointRepository.getTimeOnRouteByPackageId(packageCheckpoint.getCheckpoint().getId()))
                .thenReturn(
                        null
                );

        PackageCheckpoint testPackageCheckpoint = packageCheckpointService.getPackageCheckpointWithTimeOnRoute(packageCheckpoint.getCheckpoint().getId());
        assertEquals(
                "00:00:00",
                testPackageCheckpoint.getTimeOnCheckpoint().toString()
        );
    }

    @Test
    void processTimeRequired(){
        exception = null;
        try{
            packageCheckpointService.process(new PackageCheckpoint());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Tiempo es un campo obligatorio", exception.getMessage());
    }

    @Test
    void processFullQueueNextCheckpoint() throws Exception {
        exception = null;
        Checkpoint nextCheckpoint = new Checkpoint(2L, "Siguiente Punto de Control", 50D, 10, 10, true, null, route);

        Mockito.when(
                packageCheckpointRepository.findByCheckpointIdAndPackagesId(
                        packageCheckpoint.getCheckpoint().getId(),
                        packageCheckpoint.getPackages().getId()
                )
        ).thenReturn(packageCheckpoint);

        Mockito.doReturn(nextCheckpoint.getId()).when(packageCheckpointRepository).getNextCheckpointId(
                packageCheckpoint.getPackages().getId(),
                packageCheckpoint.getCheckpoint().getRoute().getId()
        );

        Mockito.doReturn(nextCheckpoint).when(checkpointService).getCheckpointById(
                nextCheckpoint.getId()
        );

        try{
            packageCheckpointService.process(packageCheckpoint);
        } catch (Exception e){
            exception = e;
        }
        assertEquals("No se puede procesar el paquete. La cola del siguiente punto de control se encuentra llena.", exception.getMessage());
    }

    @Test
    void processToCheckpoint() throws Exception {
        Checkpoint nextCheckpoint = new Checkpoint(2L, "Siguiente Punto de Control", 50D, 10, 9, true, null, route);

        Mockito.when(
                packageCheckpointRepository.findByCheckpointIdAndPackagesId(
                        packageCheckpoint.getCheckpoint().getId(),
                        packageCheckpoint.getPackages().getId()
                )
        ).thenReturn(packageCheckpoint);

        Mockito.doReturn(nextCheckpoint.getId()).when(packageCheckpointRepository).getNextCheckpointId(
                packageCheckpoint.getPackages().getId(),
                packageCheckpoint.getCheckpoint().getRoute().getId()
        );

        Mockito.doReturn(nextCheckpoint).when(checkpointService).getCheckpointById(
                nextCheckpoint.getId()
        );

        packageCheckpointService.process(packageCheckpoint);
    }

    @Test
    void processToDestination() throws Exception {
        Checkpoint nextCheckpoint = new Checkpoint(2L, "Siguiente Punto de Control", 50D, 10, 9, true, null, route);

        Mockito.when(
                packageCheckpointRepository.findByCheckpointIdAndPackagesId(
                        packageCheckpoint.getCheckpoint().getId(),
                        packageCheckpoint.getPackages().getId()
                )
        ).thenReturn(packageCheckpoint);

        Mockito.doReturn(null).when(packageCheckpointRepository).getNextCheckpointId(
                packageCheckpoint.getPackages().getId(),
                packageCheckpoint.getCheckpoint().getRoute().getId()
        );

        Mockito.doReturn(packageCheckpoint.getPackages()).when(packageService).getById(packageCheckpoint.getPackages().getId());

        Mockito.doReturn(route).when(routeService).update(packages.getRoute());

        assertEquals(route, routeService.update(packages.getRoute()));
        packageCheckpointService.process(packageCheckpoint);
    }

}