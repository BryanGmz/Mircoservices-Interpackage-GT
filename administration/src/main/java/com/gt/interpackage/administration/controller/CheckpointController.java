package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Checkpoint;
import com.gt.interpackage.administration.service.CheckpointService;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
