package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.handlers.QueueHandler;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/queue")
public class QueueController {

    @Autowired
    private QueueHandler queueHandler;

    @GetMapping("/")
    public ResponseEntity<?> prueba(){
        try {
            queueHandler.traverseQueue();
            return ResponseEntity.ok("Cola Recorrida");
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
