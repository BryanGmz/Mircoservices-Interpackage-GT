package com.gt.interpackage.administration.handlers;

import com.gt.interpackage.administration.model.*;
import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.service.*;
import com.gt.interpackage.administration.source.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QueueHandler {

    @Autowired
    private PackageService packageService;

    @Autowired
    private QueueService queueService;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private PackageCheckpointService packageCheckpointService;

    @Autowired
    private RouteService routeService;

    /*
    Metodo para recorrer la cola, y comprobar si hay espacio para ser ingresado
    */
    public void traverseQueue() throws Exception {
        List<Destination> destinations = destinationService.findAll();
        for (Destination destination : destinations) {
            List<Queue> queue = queueService.findByDestination(destination.getId());
            for (Queue packageOnQueue : queue) {
                List<Checkpoint> checkpoints = checkpointService.getAllCheckpointsByDestinationId(destination.getId());
                if (!checkpoints.isEmpty()) {
                    Checkpoint checkpoint = getCheckpointWithQueueAvailable(checkpoints);
                    if (checkpoint != null) {
                        Package packet = packageOnQueue.getPackages();
                        addPackageInQueue(checkpoint, packet);
                        Route route = increasePackageInRouteCounter(checkpoint.getRoute().getId());
                        increaseTheQueuedPacketCounterOfACheckpoint(checkpoint);
                        updatePackageOnWay(packet, route);
                        queueService.deletePackageOnQueue(packageOnQueue);
                    }
                }
            }
        }
    }

    public void updatePackageOnWay(Package packet, Route route) throws Exception {
        packet.setOnWay(true);
        packet.setDateStart(LocalDate.now());
        packet.setRoute(route);
        packageService.update(packet, packet.getId());
    }

    public void addPackageInQueue(Checkpoint checkpoint, Package packet) {
        PackageCheckpoint packageCheckpoint = new PackageCheckpoint(checkpoint, packet, null, true);
        packageCheckpointService.create(packageCheckpoint);
    }

    public Route increasePackageInRouteCounter(Long idRoute) throws BadRequestException {
        Route route = routeService.getRouteById(idRoute);
        route.setPackagesOnRoute(route.getPackagesOnRoute() + 1);
        route.setTotalPackages(route.getTotalPackages() + 1);
        routeService.save(route);
        return route;
    }

    public void increaseTheQueuedPacketCounterOfACheckpoint(Checkpoint checkpoint) throws Exception {
        checkpoint.setPackagesOnQueue(checkpoint.getPackagesOnQueue() + 1);
        checkpointService.create(checkpoint);
    }

    public Checkpoint getCheckpointWithQueueAvailable(List<Checkpoint> checkpoints) {
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.getQueueCapacity() > checkpoint.getPackagesOnQueue()) return checkpoint;
        } return null;
    }

}
