package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Route;
import com.gt.interpackage.operator.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    private Route route;
    private Exception exception;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        route = new Route(1L, "Ruta1", 0,0,true, null);
    }

    @Test
    void create() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.doReturn(false).when(routeRepository).existsRouteByNameAndIdIsNot(route.getName(), route.getId());
        Mockito.doReturn(route).when(routeRepository).save(route);
        assertEquals(route.getId(), routeService.create(route).getId());
    }

    @Test
    void update() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.doReturn(false).when(routeRepository).existsRouteByNameAndIdIsNot(route.getName(), route.getId());
        Mockito.when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        Mockito.doReturn(route).when(routeRepository).save(route);
        assertEquals(route.getId(), routeService.update(route).getId());
    }

    @Test
    void existsAndIdIsNot() {
    }

    @Test
    void validateRouteData() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.doReturn(false).when(routeRepository).existsRouteByNameAndIdIsNot(route.getName(), route.getId());
        routeService.validateRouteData(route);
    }

    @Test
    void validateRouteDataNoValidName() {
        exception = null;
        try{
            routeService.validateRouteData(new Route(1L, "", 0,0,true, null));
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de ruta no valido", exception.getMessage());
    }

    @Test
    void validateRouteDataRouteAlreadyExist() {
        exception = null;
        Mockito.doReturn(true).when(routeRepository).existsRouteByNameAndIdIsNot(route.getName(), route.getId());
        try{
            routeService.validateRouteData(route);
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de ruta ya registrado en el sistema", exception.getMessage());
    }

    @Test
    void getRouteById() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        Route requestedRoute = routeService.getRouteById(route.getId());
        assertEquals(route.getId(), requestedRoute.getId());
    }

    @Test
    void getRouteByIdNotExistRoute()  {
        exception = null;
        Mockito.when(routeRepository.findById(route.getId())).thenReturn(Optional.empty());
        try{
            routeService.getRouteById(route.getId());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("La ruta no existe en el sistema.", exception.getMessage());
    }
}