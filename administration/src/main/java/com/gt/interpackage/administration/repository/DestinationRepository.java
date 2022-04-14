package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    public boolean existsDestinationByName(String name);
}