package com.gt.interpackage.operator.controller;

import com.gt.interpackage.operator.model.PackageCheckpoint;
import com.gt.interpackage.operator.service.PackageCheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = com.gt.interpackage.administration.source.Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping(com.gt.interpackage.administration.source.Constants.API_V1_ADMIN + "/package-checkpoint")
public class PackageCheckpointController {

    @Autowired
    private PackageCheckpointService packageCheckpointService;


    @GetMapping("list/{id}")
    public ResponseEntity<List<PackageCheckpoint>> getAllPackageCheckpointOnCheckpoint(@PathVariable Long id){
        try{
            return ResponseEntity.ok(packageCheckpointService.getAllPackageCheckpointOnCheckpoint(id));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageCheckpoint> getPackageCheckpoint(@PathVariable Long id){
        try{
            return ResponseEntity.ok(packageCheckpointService.getPackageCheckpointWithTimeOnRoute(id));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping()
    public ResponseEntity<PackageCheckpoint> processPackage(@RequestBody PackageCheckpoint packageCheckpoint){
        try{
            packageCheckpointService.process(packageCheckpoint);
            return ResponseEntity.ok().build();
        } catch(com.gt.interpackage.administration.source.BadRequestException b){
            return new ResponseEntity(b.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

