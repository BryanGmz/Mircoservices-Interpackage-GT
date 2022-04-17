package com.gt.interpackage.reports.controller;

import com.gt.interpackage.reports.dto.FilterDateDTO;
import com.gt.interpackage.reports.dto.TopRouteDTO;
import com.gt.interpackage.reports.model.Route;
import com.gt.interpackage.reports.service.RouteService;
import com.gt.interpackage.reports.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(Constants.API_V1_REPORTS + "/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/most-popular-route")
    public ResponseEntity<List<TopRouteDTO>> getMostPopularRouteFilter(@RequestBody FilterDateDTO dto) {
        try {
            return ResponseEntity.ok(routeService.getTopRoute(entityManager, dto.getStart(), dto.getEnd()));
        } catch (ParseException e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * Metodo que recibe una peticion GET para obtener un listado paginado de rutas.
     * @param page Numero de pagina actual. Por defecto 1.
     * @param size Tamaño de la pagina. Por defecto 10.
     * @return
     */
    @CrossOrigin
    @GetMapping("list")
    public ResponseEntity<Page<Route>> getRoutes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            Page<Route> routes = routeService.getAll(
                    PageRequest.of(page, size, Sort.by("name"))
            );
            return new ResponseEntity<>(routes, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping("list/{active}")
    public ResponseEntity<Page<Route>> getRoutesByActive (
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Boolean active
    ) {
        try {
            Page<Route> routes = routeService.getRoutesByActive(
                    PageRequest.of(page, size, Sort.by("name")), active
            );
            return new ResponseEntity<Page<Route>>(routes, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
