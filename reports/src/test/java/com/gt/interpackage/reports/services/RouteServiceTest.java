package com.gt.interpackage.reports.services;

import com.gt.interpackage.reports.dto.TopRouteDTO;
import com.gt.interpackage.reports.service.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

    private TopRouteDTO topRouteDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        topRouteDTO = new TopRouteDTO(125, "Ruta 1", "GT-IZA");
        entityManager = Mockito.mock(EntityManager.class);
        query = Mockito.mock(Query.class);
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

}
