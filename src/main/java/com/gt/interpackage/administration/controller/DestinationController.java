package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.service.DestinationService;
import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RequestMapping (Constants.API_V1_ADMIN + "/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping("/")
    public ResponseEntity<List<Destination>> getAllDestinations(){
        return ResponseEntity.ok(destinationService.findAll());
    }
    
    
    @PostMapping("/")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        try {
            return destinationService.service(destination, false, null);
        } catch (Exception e) {
            // Error 500 Internal Server Error
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PatchMapping
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination){
        try{
            return ResponseEntity.ok(destinationService.update(destination));
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Destination> deleteDestination(@PathVariable Long id){
        try{
            destinationService.delete(id);
            return ResponseEntity.ok().build();
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Destination> getDestination(@PathVariable Long id){
        try{
            return ResponseEntity.ok(destinationService.getDestinationById(id));
        }  catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/search-by-name/{name}")
    public ResponseEntity<List<Destination>> getDestinationByName(@PathVariable String name){
        try{
            return ResponseEntity.ok(destinationService.findByName(name));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list")
    public ResponseEntity<Page<Destination>> getDestinations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(destinationService.getAll(page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
