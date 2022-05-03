package com.gt.interpackage.administration.controller;


import com.gt.interpackage.administration.model.Route;
import com.gt.interpackage.administration.service.CheckpointService;
import com.gt.interpackage.administration.service.DestinationService;
import com.gt.interpackage.administration.service.RouteService;
import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping(Constants.API_V1_ADMIN + "/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private DestinationService destinationService;

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route){
        try{
            Route tempRoute = routeService.create(route, destinationService);
            return ResponseEntity.created(new URI("/route/"+tempRoute.getId())).body(tempRoute);
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<Route> updateRoute(@RequestBody Route route){
        try{
            Route updatedRoute = routeService.update(route, destinationService);
            return ResponseEntity.ok(updatedRoute);
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Route> deleteRoute(@PathVariable Long id){
        try{
            routeService.delete(id, checkpointService);
            return ResponseEntity.ok().build();
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/search-by-name/{name}")
    public ResponseEntity<List<Route>> getRoutesByName(@PathVariable String name){
        try{
            return ResponseEntity.ok(routeService.getAllByName(name).get());
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id){
        try{
            return ResponseEntity.ok(routeService.getRouteById(id));
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list")
    public ResponseEntity<Page<Route>> getRoutes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(routeService.getAll(page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/destination/{id_destination}")
    public ResponseEntity<List<Route>> getRoutesByDestination(@PathVariable Integer id_destination){
        return ResponseEntity.ok(routeService.findRouteByDestination(id_destination));
    }
}
