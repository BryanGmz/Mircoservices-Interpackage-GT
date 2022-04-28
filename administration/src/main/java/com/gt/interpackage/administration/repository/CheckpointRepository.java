package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

    public boolean existsCheckpointByRouteIdAndDescription(Long routeId, String description);

    public boolean existsCheckpointByRouteId(Long routeId);

    public List<Checkpoint> findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(Long cui);

    public List<Checkpoint> findAllByRoute_Destination_IdAndRoute_ActiveAndActiveOrderById(Long idDestination, Boolean activeRoute, Boolean activeCheckpoint);
}
