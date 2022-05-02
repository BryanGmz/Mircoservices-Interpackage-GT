package com.gt.interpackage.operator.handlers;

import java.time.LocalDate;
import java.util.List;

import com.gt.interpackage.operator.model.*;
import com.gt.interpackage.operator.model.Package;
import com.gt.interpackage.operator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private RouteService routeService;

    private void traverseQueue(PackageCheckpointService packageCheckpointService) throws Exception {
        List<Destination> destinations = destinationService.findAll();
        for (Destination destination : destinations) {
            List<Queue> queue = queueService.findByDestination(destination.getId());
            for (Queue packageOnQueue : queue) {
                List<Checkpoint> checkpoints = checkpointService.getAllCheckpointsByDestinationId(destination.getId());
                if (!checkpoints.isEmpty()) {
                    Checkpoint checkpoint = getCheckpointWithQueueAvailable(checkpoints);
                    if (checkpoint != null) {
                        Package p = packageOnQueue.getPackages();
                        PackageCheckpoint packageCheckpoint = new PackageCheckpoint(checkpoint, p, null, true);
                        packageCheckpointService.create(packageCheckpoint);
                        Route route = routeService.getRouteById(checkpoint.getRoute().getId());
                        route.setPackagesOnRoute(route.getPackagesOnRoute() + 1);
                        route.setTotalPackages(route.getTotalPackages() + 1);
                        routeService.create(route);
                        checkpoint.setPackagesOnQueue(checkpoint.getPackagesOnQueue() + 1);
                        checkpointService.create(checkpoint);
                        p.setOnWay(true);
                        p.setDateStart(LocalDate.now());
                        p.setRoute(route);
                        packageService.update(p, p.getId());
                        queueService.deletePackageOnQueue(packageOnQueue);
                    }
                }
            }
        }
    }

    public void verifiyQueue(PackageCheckpointService packageCheckpointService) {
        try {
            traverseQueue(packageCheckpointService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Checkpoint getCheckpointWithQueueAvailable(List<Checkpoint> checkpoints) {
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.getQueueCapacity() > checkpoint.getPackagesOnQueue()) return checkpoint;
        } return null;
    }
    
}
