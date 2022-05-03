package com.gt.interpackage.administration.handler;

import com.gt.interpackage.administration.handlers.QueueHandler;
import com.gt.interpackage.administration.model.*;
import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.service.*;
import com.gt.interpackage.administration.source.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class QueueHandlerTest {

    @Mock
    private PackageService packageService;

    @Mock
    private QueueService queueService;

    @Mock
    private DestinationService destinationService;

    @Mock
    private CheckpointService checkpointService;

    @Mock
    private PackageCheckpointService packageCheckpointService;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private QueueHandler queueHandler;

    private Destination destination;
    private Queue queue;
    private Checkpoint checkpoint;
    private Package packet;
    private Route route;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GT-Xela", "De Guatemala a Xela", 15.5);
        route = new Route(1L, "Ruta 1", 15, 15, true, destination);
        checkpoint = new Checkpoint(1L, "Punto de Control 1", 15.50, 15, 12, true, new Employee(), route);
        packet = new Package(1L, false, true, false, 15.50, 12.50, false, "Audifonos", new Invoice(), 15.50, route, destination);
        queue = new Queue(1L, packet, 1);
    }

    @Test
    public void testUpdatePackageOnWay() throws Exception {
        Mockito.when(
                packageService.update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class)))
                .thenReturn(packet);
        queueHandler.updatePackageOnWay(packet, route);
        Mockito.verify(packageService).update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testIncreasePackageInRouteCounter() throws BadRequestException {
        Mockito.when(
                routeService.getRouteById(ArgumentMatchers.any(Long.class)))
                .thenReturn(route);
        Mockito.when(
                routeService.save(ArgumentMatchers.any(Route.class)))
                .thenReturn(route);
        Route updated = queueHandler.increasePackageInRouteCounter(1L);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(updated.getPackagesOnRoute(), 16);
        Assertions.assertEquals(updated.getTotalPackages(), 16);
        Mockito.verify(routeService).getRouteById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).save(ArgumentMatchers.any(Route.class));
    }

    @Test
    public void testIncreaseTheQueuedPacketCounterOfACheckpoint() throws Exception {
        Mockito.when(checkpointService.create(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(ResponseEntity.ok(checkpoint));
        queueHandler.increaseTheQueuedPacketCounterOfACheckpoint(checkpoint);
        Mockito.verify(checkpointService).create(ArgumentMatchers.any(Checkpoint.class));
    }

    @Test
    public void testGetCheckpointWithQueueAvailableSuccessful() {
        checkpoint.setQueueCapacity(1000);
        Checkpoint getCheckpoint = queueHandler.getCheckpointWithQueueAvailable(Arrays.asList(checkpoint));
        Assertions.assertNotNull(getCheckpoint);
        Assertions.assertTrue(getCheckpoint.getQueueCapacity() > getCheckpoint.getPackagesOnQueue());
    }

    @Test
    public void testGetCheckpointWithQueueAvailableButNotFound() {
        checkpoint.setQueueCapacity(1);
        Checkpoint getCheckpoint = queueHandler.getCheckpointWithQueueAvailable(Arrays.asList(checkpoint));
        Assertions.assertNull(getCheckpoint);
    }

    @Test
    public void testTraverseQueue() throws Exception {
        Mockito.when(
                destinationService.findAll())
                .thenReturn(Arrays.asList(destination));
        Mockito.when(
                queueService.findByDestination(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(queue));
        Mockito.when(
                checkpointService.getAllCheckpointsByDestinationId(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(checkpoint));
        Mockito.when(
                routeService.getRouteById(ArgumentMatchers.any(Long.class)))
                .thenReturn(route);
        Mockito.when(
                routeService.save(ArgumentMatchers.any(Route.class)))
                .thenReturn(route);
        Mockito.when(
                packageService.update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class)))
                .thenReturn(packet);
        queueHandler.traverseQueue();
        Mockito.verify(destinationService).findAll();
        Mockito.verify(queueService).findByDestination(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointService).getAllCheckpointsByDestinationId(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).getRouteById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).save(ArgumentMatchers.any(Route.class));
        Mockito.verify(packageService).update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class));
    }
}
