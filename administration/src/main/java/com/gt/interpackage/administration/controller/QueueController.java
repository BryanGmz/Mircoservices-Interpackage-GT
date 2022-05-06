package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.handlers.QueueHandler;
import com.gt.interpackage.administration.model.Queue;
import com.gt.interpackage.administration.service.QueueService;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/queue")
public class QueueController {

    @Autowired
    private QueueHandler queueHandler;

    @Autowired
    private QueueService queueService;

    @GetMapping("/")
    public ResponseEntity<?> prueba(){
        try {
            queueHandler.traverseQueue();
            return ResponseEntity.ok("Cola Recorrida");
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Queue> addQueue(@RequestBody Queue queue) {
        try {
            Queue savedQueue = queueService.save(queue);
            return ResponseEntity
                    .created(new URI("/queue/" + savedQueue.getQueue()))
                    .body(savedQueue);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

}
