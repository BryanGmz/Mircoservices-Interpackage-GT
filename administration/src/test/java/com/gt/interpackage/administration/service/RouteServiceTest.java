package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.model.Fee;
import com.gt.interpackage.administration.model.Route;
import com.gt.interpackage.administration.repository.CheckpointRepository;
import com.gt.interpackage.administration.repository.DestinationRepository;
import com.gt.interpackage.administration.repository.RouteRepository;
import com.gt.interpackage.administration.source.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private CheckpointRepository checkpointRepository;

    @InjectMocks
    private RouteService routeService;

    @InjectMocks
    private DestinationService destinationService;

    @InjectMocks
    private CheckpointService checkpointService;

    private Route route;
    private Destination destination;
    private Exception exception;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "Xela", "Xela", 15.50);
        route = new Route(1L, "Ruta 1", 15, 35, true, destination);

    }

    @Test
    public void testExistsById(){
        Long routeId = 1L;
        Mockito.doReturn(true).when(routeRepository).existsById(routeId);

        Boolean exist = routeService.existsById(routeId);
        assertEquals(exist, true);
    }

    @Test
    public void testExistsByName(){
        String name = "Xela";

        Mockito.doReturn(true).when(routeRepository).existsRouteByName(name);
        assertEquals(routeService.existsByName(name), true);

        Mockito.doReturn(false).when(routeRepository).existsRouteByName(name);
        assertEquals(routeService.existsByName(name), false);
    }

    @Test
    public void testExistsAndIsNot(){
        String name = "Xela";
        Long id = 1L;

        Mockito.doReturn(true).when(routeRepository).existsRouteByNameAndIdIsNot(name, id);
        assertEquals(routeService.existsAndIdIsNot(name, id), true);

        Mockito.doReturn(false).when(routeRepository).existsRouteByNameAndIdIsNot(name, id);
        assertEquals(routeService.existsAndIdIsNot(name, id), false);
    }

    @Test
    public void testGetAllByName(){
        String name = "X";

        Mockito.when(
            routeRepository.findByNameStartingWith(name))
            .thenReturn(Optional.of(Arrays.asList(route))
        );

        List<Route> routes = routeService.getAllByName(name).get();

        assertEquals(routes.size(), 1);
        assertEquals(routes.get(0).getName(), route.getName());
    }

    @Test
     void testGetRouteById() throws BadRequestException {
        Long routeId = 1L;
        Mockito.doReturn(Optional.of(route)).when(routeRepository).findById(routeId);

        Route searchedRoute = routeService.getRouteById(routeId);
        assertNotNull(searchedRoute);
        assertEquals(searchedRoute.getId(), routeId);
    }

    @Test
    public void testGetRouteByIdNotFoundRoute(){
        exception = null;
        try {
            Route searchedRoute = routeService.getRouteById(1L);
        } catch (BadRequestException e) {
            exception = e;
        }
        assertEquals(exception.getMessage(), "La ruta no existe en el sistema.");
    }

    @Test
    public void testRouteHasDestinationAssigned(){
        long idTrue = 1L;
        long idFlalse = 2L;
        Mockito.doReturn(true).when(routeRepository).existsRouteByDestinationId(idTrue);

        assertEquals(routeService.routeHasDestinationAssigned(idTrue), true);
        assertEquals(routeService.routeHasDestinationAssigned(idFlalse), false);

    }

    @Test
    public void testValidateNoPackagesOnRoute(){
        exception = null;
        try{
            routeService.validateNoPackagesOnRoute(route);
        } catch (Exception e){
            exception = e;
        }
        assertEquals(exception.getMessage(), "No se pueden realizar acciones sobre una ruta que contiene paquetes en ruta.");

    }

    @Test
    public void testFindRouteByDestination(){
        int destinationId = 1;
        Mockito.doReturn(Arrays.asList(route)).when(routeRepository).findRouteByDestination(destinationId);

        List<Route> routes = routeService.findRouteByDestination(destinationId);
        assertEquals(routes.size(), 1);
        assertEquals(routes.get(0).getId(), 1L);
    }

    @Test
    public void testSave(){
        Mockito.doReturn(route).when(routeRepository).save(route);

        Route savedRoute = routeService.save(route);
        assertEquals(route.getId(), savedRoute.getId());
        assertEquals(route.getName(), savedRoute.getName());
    }

    @Test
    public void testValidateRouteNoValidName(){
        exception = null;
        try{
            routeService.validateRouteData(
                new Route(1L, "", 0, 0, true, destination)
                ,null);
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de ruta no valido", exception.getMessage());
    }

    @Test
    public void testValidateRouteAlreadyExistRouteName(){
        exception = null;
        long id = 1L;
        String name = "X";
        Mockito.doReturn(true).when(routeRepository).existsRouteByNameAndIdIsNot(name, id);
        try{
            routeService.validateRouteData(
                new Route(1L, "X", 0, 0, true, destination)
                ,null);
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de ruta ya registrado en el sistema", exception.getMessage());
    }

    @Test
    public void testDelete() throws BadRequestException {
        exception = null;
        Long id = 1L;
        Mockito.doReturn(false).when(checkpointRepository).existsCheckpointByRouteId(id);
        Mockito.doReturn(
            Optional.of(new Route(
                    1L, "Ruta 1", 0, 0, true, destination
            ))).when(routeRepository).findById(id);
            routeService.delete(id, checkpointService);
    }

    @Test
    public void testDeleteRouteHasCheckpointsAssigned(){
        exception = null;
        Long id = 1L;
        Mockito.doReturn(true).when(checkpointRepository).existsCheckpointByRouteId(id);
        Mockito.doReturn(Optional.of(route)).when(routeRepository).findById(id);
        try{
            routeService.delete(id, checkpointService);
        } catch (Exception e){
            exception = e;
        }
        assertEquals("No se puede eliminar la ruta ya que tiene puntos de control asignados.", exception.getMessage());
    }

    @Test
    public void testCreate() throws BadRequestException {
        Mockito.doReturn(Optional.of(destination)).when(destinationRepository).findById(destination.getId());
        Mockito.doReturn(route).when(routeRepository).save(route);
        Route savedRoute = routeService.create(route, destinationService);
        assertEquals(route.getId(), savedRoute.getId());
    }

    @Test
    public void testUpdate() throws BadRequestException {
        Route tempRoute = new Route(1L, "Ruta 1", 0, 0, true, destination);
        Mockito.doReturn(Optional.of(tempRoute)).when(routeRepository).findById(tempRoute.getId());
        Mockito.doReturn(Optional.of(destination)).when(destinationRepository).findById(destination.getId());
        Mockito.when(routeRepository.save(tempRoute)).thenReturn((tempRoute));
        Route updateRoute = routeService.update(tempRoute, destinationService);
        assertEquals(tempRoute.getId(), updateRoute.getId());
    }

}