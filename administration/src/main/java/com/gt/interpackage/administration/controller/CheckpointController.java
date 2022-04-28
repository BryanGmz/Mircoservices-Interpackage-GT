package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Checkpoint;
import com.gt.interpackage.administration.service.CheckpointService;
import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/checkpoint")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @PutMapping (value = "update/{id}")
    public ResponseEntity<Checkpoint> updateCheckpoint(@RequestBody Checkpoint update, @PathVariable Long id) {
        try {
            return checkpointService.updateCheckpoint(update, id, false);
        } catch (Exception e) {
            // Error 500 Internal Server Error
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping (value = "operator/{id}")
    public ResponseEntity<Checkpoint> updateAssignmentOperatorCheckpoint(@RequestBody Checkpoint update, @PathVariable Long id) {
        try {
            return checkpointService.updateCheckpoint(update, id, true);
        } catch (Exception e) {
            // Error 500 Internal Server Error
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Checkpoint> createCheckpoint(@RequestBody Checkpoint checkpoint){
        try{
            return checkpointService.create(checkpoint);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Checkpoint> deleteCheckpoint(@PathVariable Long id){
        try{
            checkpointService.delete(id);
            return ResponseEntity.ok().build();
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Checkpoint> getCheckpoint(@PathVariable Long id){
        try{
            return new ResponseEntity<Checkpoint>(checkpointService.getCheckpointById2(id), HttpStatus.OK);
        } catch(BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list")
    public ResponseEntity<Page<Checkpoint>> getCheckpoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(checkpointService.getAll(page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
