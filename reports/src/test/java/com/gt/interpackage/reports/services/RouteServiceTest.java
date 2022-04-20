package com.gt.interpackage.reports.services;

import com.gt.interpackage.reports.dto.TopRouteDTO;
import com.gt.interpackage.reports.model.Destination;
import com.gt.interpackage.reports.model.Route;
import com.gt.interpackage.reports.repository.RouteRepository;
import com.gt.interpackage.reports.service.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Mock
    private RouteRepository routeRepository;

    private Route route;
    private Destination destination;
    private Page<Route> page;
    private TopRouteDTO topRouteDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        topRouteDTO = new TopRouteDTO(125, "Ruta 1", "GT-IZA");
        entityManager = Mockito.mock(EntityManager.class);
        query = Mockito.mock(Query.class);
        destination = new Destination(1L, "GUATEMALA-PETEN", "De Gautemala a Peten", 52.50);
        route = new Route(1L, "Ruta 1", 45, 150, true, destination);
        page = new PageImpl<>(Arrays.asList(route), PageRequest.of(1, 10), 1L);
    }

    @Test
    public void testGetTopRouteWithoutFilters() throws ParseException {
        Mockito.when(
                entityManager.createNativeQuery(ArgumentMatchers.any(String.class)))
                .thenReturn(query);
        Mockito.when(
                query.getResultList())
                .thenReturn(Arrays.asList(topRouteDTO));
        List<TopRouteDTO> topRoutes = routeService.getTopRoute(entityManager, null, null);
        Assertions.assertNotNull(topRoutes);
        Assertions.assertEquals(topRoutes.size(), 1);
    }

    @Test
    public void testGetTopRouteOnlyWithTheInitialDate() throws ParseException {
        Mockito.when(
                        entityManager.createNativeQuery(ArgumentMatchers.any(String.class)))
                .thenReturn(query);
        Mockito.when(
                        query.getResultList())
                .thenReturn(Arrays.asList(topRouteDTO));
        List<TopRouteDTO> topRoutes = routeService.getTopRoute(entityManager, "2020-02-02", null);
        Assertions.assertNotNull(topRoutes);
        Assertions.assertEquals(topRoutes.size(), 1);
    }

    @Test
    public void testGetTopRouteWithFilters() throws ParseException {
        Mockito.when(
                        entityManager.createNativeQuery(ArgumentMatchers.any(String.class)))
                .thenReturn(query);
        Mockito.when(
                        query.getResultList())
                .thenReturn(Arrays.asList(topRouteDTO));
        List<TopRouteDTO> topRoutes = routeService.getTopRoute(entityManager, "2020-02-02", "2021-01-01");
        Assertions.assertNotNull(topRoutes);
        Assertions.assertEquals(topRoutes.size(), 1);
    }

    @Test
    public void testGetAllRoutes() {
        Mockito.when(
                routeRepository
                        .findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);
        Page<Route> pageResult = routeService.getAll(PageRequest.of(10, 15, Sort.by("name")));
        Assertions.assertNotNull(pageResult);
        Assertions.assertEquals(pageResult.getContent().size(), 1);
    }

    @Test
    public void testGetRoutesByActiveTrue() {
        Mockito.when(
                        routeRepository
                                .findAllByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class)))
                .thenReturn(page);
        Page<Route> pageResult = routeService.getRoutesByActive(PageRequest.of(10, 15, Sort.by("name")), true);
        Assertions.assertNotNull(pageResult);
        Assertions.assertEquals(pageResult.getContent().size(), 1);
    }

    @Test
    public void testGetRoutesByActiveFalse() {
        Mockito.when(
                        routeRepository
                                .findAllByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class)))
                .thenReturn(page);
        Page<Route> pageResult = routeService.getRoutesByActive(PageRequest.of(10, 15, Sort.by("name")), false);
        Assertions.assertNotNull(pageResult);
        Assertions.assertEquals(pageResult.getContent().size(), 1);
    }
}
