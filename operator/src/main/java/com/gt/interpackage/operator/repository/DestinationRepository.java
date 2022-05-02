package com.gt.interpackage.operator.repository;

import com.gt.interpackage.operator.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

}