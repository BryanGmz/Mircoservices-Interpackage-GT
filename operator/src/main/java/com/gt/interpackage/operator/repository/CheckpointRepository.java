package com.gt.interpackage.operator.repository;

import com.gt.interpackage.operator.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

    public List<Checkpoint> findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(Long cui);

}
