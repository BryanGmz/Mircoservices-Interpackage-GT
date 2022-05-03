package com.gt.interpackage.operator.repository;

import com.gt.interpackage.operator.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    public boolean existsRouteByNameAndIdIsNot(String name, Long id);

}
