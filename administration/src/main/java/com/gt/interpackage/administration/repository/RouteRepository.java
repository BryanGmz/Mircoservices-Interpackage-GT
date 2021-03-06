package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    public Optional<List<Route>> findByNameStartingWith(String name);

    public boolean existsRouteByName(String name);

    public boolean existsRouteByNameAndIdIsNot(String name, Long id);

    public boolean existsRouteByDestinationId(Long destinationId);
    
    @Query(value = "SELECT * FROM route r WHERE r.id_destination =?1", nativeQuery = true)
    List<Route> findRouteByDestination(Integer id_destination);

}
