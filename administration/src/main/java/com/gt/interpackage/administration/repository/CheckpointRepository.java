package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

    public boolean existsCheckpointByRouteIdAndDescription(Long routeId, String description);
}
