package com.gt.interpackage.receptionist.controller;

import com.gt.interpackage.receptionist.model.PackageCheckpoint;
import com.gt.interpackage.receptionist.service.PackageCheckpointService;
import com.gt.interpackage.receptionist.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping(Constants.API_V1_RECEP + "/package-checkpoint")
public class PackageCheckpointController {

    @Autowired
    private PackageCheckpointService packageCheckpointService;

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<PackageCheckpoint> getPackageCheckpoint(@PathVariable Long id){
        try{
            return ResponseEntity.ok(packageCheckpointService.getPackageCheckpointWithTimeOnRoute(id));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
