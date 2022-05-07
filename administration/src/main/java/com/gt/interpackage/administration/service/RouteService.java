package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Route;
import com.gt.interpackage.administration.repository.RouteRepository;
import com.gt.interpackage.administration.source.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;


    public boolean existsById(Long id){
        return routeRepository.existsById(id);
    }

    public Route create(Route route, DestinationService destinationService) throws BadRequestException {
        this.validateRouteData(route, destinationService);
        return routeRepository.save(route);
    }

    public Route update(Route route, DestinationService destinationService) throws BadRequestException{
        this.validateRouteData(route, destinationService);
        Route updatedRoute = this.getRouteById(route.getId());
        this.validateNoPackagesOnRoute(updatedRoute);
        updatedRoute.setName(route.getName());
        updatedRoute.setActive(route.getActive());
        updatedRoute.setDestination(route.getDestination());
        return routeRepository.save(updatedRoute);
    }

    public Route save(Route route){
        return routeRepository.save(route);
    }


    public void delete(Long id, CheckpointService checkpointService) throws BadRequestException{
        Route tempRoute = this.getRouteById(id);

        if(checkpointService.routeHasCheckpointsAssigned(id))
            throw  new BadRequestException("No se puede eliminar la ruta ya que tiene puntos de control asignados.");

        this.validateNoPackagesOnRoute(tempRoute);
        routeRepository.delete(tempRoute);
    }

    public Page<Route> getAll(int page, int size){
        return routeRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Optional<List<Route>> getAllByName(String name){
        return routeRepository.findByNameStartingWith(name);
    }

    public boolean existsByName(String name){
        return routeRepository.existsRouteByName(name);
    }

    public boolean existsAndIdIsNot(String name, Long id){
        return routeRepository.existsRouteByNameAndIdIsNot(name, id);
    }

    public Route getRouteById(Long id) throws BadRequestException{
        Optional<Route> route = routeRepository.findById(id);
        if(route.isEmpty())
            throw  new BadRequestException("La ruta no existe en el sistema.");
        return route.get();
    }

    public boolean routeHasDestinationAssigned(Long destinationId){
        return routeRepository.existsRouteByDestinationId(destinationId);
    }

    public void validateRouteData(Route route, DestinationService destinationService) throws BadRequestException{
        if(route.getName().isBlank() || route.getName().isEmpty())
            throw new BadRequestException("Nombre de ruta no valido");

        if(this.existsAndIdIsNot(route.getName(), route.getId()))
            throw new BadRequestException("Nombre de ruta ya registrado en el sistema");

        destinationService.getDestinationById(route.getDestination().getId());
    }

    public void validateNoPackagesOnRoute(Route route) throws BadRequestException{
        if(route.getPackagesOnRoute() > 0)
            throw new BadRequestException("No se pueden realizar acciones sobre una ruta que contiene paquetes en ruta.");
    }
    
    public List<Route> findRouteByDestination(Integer id_destination){
        return routeRepository.findRouteByDestination(id_destination);
    }

}
