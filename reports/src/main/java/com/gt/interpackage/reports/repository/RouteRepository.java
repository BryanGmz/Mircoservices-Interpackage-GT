package com.gt.interpackage.reports.repository;

import com.gt.interpackage.reports.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    // Obtener la lista de rutas activas
    public Page<Route> findAllByActive(Pageable pageable, Boolean active);
}
