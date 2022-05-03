package com.gt.interpackage.operator.controller;

import com.gt.interpackage.administration.source.Constants;
import com.gt.interpackage.operator.model.Checkpoint;
import com.gt.interpackage.operator.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping(Constants.API_V1_ADMIN + "/checkpoint")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @CrossOrigin
    @GetMapping("list/{cui}")
    public ResponseEntity<List<Checkpoint>> getAllByAssignedOperator(@PathVariable Long cui){
        try{
            return ResponseEntity.ok(checkpointService.getAllByAssignedOperator(cui));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
