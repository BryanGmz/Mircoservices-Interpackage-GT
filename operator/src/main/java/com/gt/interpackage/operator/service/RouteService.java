package com.gt.interpackage.operator.service;

import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.operator.model.Route;
import com.gt.interpackage.operator.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Route create(Route route) throws BadRequestException{
        this.validateRouteData(route);
        return routeRepository.save(route);
    }

    public Route update(Route route) throws BadRequestException{
        this.validateRouteData(route);
        Route updatedRoute = this.getRouteById(route.getId());
        updatedRoute.setName(route.getName());
        updatedRoute.setActive(route.getActive());
        updatedRoute.setDestination(route.getDestination());
        return routeRepository.save(updatedRoute);
    }

    public boolean existsAndIdIsNot(String name, Long id){
        return routeRepository.existsRouteByNameAndIdIsNot(name, id);
    }

    public void validateRouteData(Route route) throws BadRequestException{
        if(route.getName().isBlank() || route.getName().isEmpty())
            throw new BadRequestException("Nombre de ruta no valido");

        if(this.existsAndIdIsNot(route.getName(), route.getId()))
            throw new BadRequestException("Nombre de ruta ya registrado en el sistema");
    }

    public Route getRouteById(Long id) throws BadRequestException{
        Optional<Route> route = routeRepository.findById(id);
        if(route.isEmpty())
            throw  new BadRequestException("La ruta no existe en el sistema.");
        return route.get();
    }
}
