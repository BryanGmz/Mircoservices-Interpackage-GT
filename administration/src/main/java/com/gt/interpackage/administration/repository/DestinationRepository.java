package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    public boolean existsDestinationByName(String name);

    public List<Destination> findByNameStartingWith(String name);

    public boolean existsDestinationByNameAndIdIsNot(String name, Long id);
}