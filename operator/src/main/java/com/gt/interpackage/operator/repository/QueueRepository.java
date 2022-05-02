package com.gt.interpackage.operator.repository;

import com.gt.interpackage.operator.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<com.gt.interpackage.operator.model.Queue, Long> {

    public List<Queue> findAllByPackages_Destination_Id(Long id_Destination);
}