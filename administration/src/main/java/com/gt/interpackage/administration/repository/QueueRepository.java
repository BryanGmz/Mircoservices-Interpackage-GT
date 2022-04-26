package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    public List<Queue> findAllByPackages_Destination_Id(Long id_Destination);
}