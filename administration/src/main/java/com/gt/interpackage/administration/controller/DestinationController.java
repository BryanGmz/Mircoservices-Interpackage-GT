package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.service.DestinationService;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping("/")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination) {
        try {
            return destinationService.service(destination, false, null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Error 400 Bad Request
        }
    }
}
