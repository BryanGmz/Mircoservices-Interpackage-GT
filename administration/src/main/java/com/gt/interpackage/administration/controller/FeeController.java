package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Fee;
import com.gt.interpackage.administration.service.FeeService;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/fee")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @GetMapping("/")
    public ResponseEntity<List<Fee>> getAllFees() {
        return ResponseEntity.ok(feeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fee> getFee(@PathVariable Long id) {
        try {
            Fee fee = feeService.getById(id);
            return fee != null ?
                    // 200 Ok
                    ResponseEntity.ok(fee) :
                     // Error 404 Not Found
                     ResponseEntity.notFound()
                             .build();
        } catch (Exception e) {
             // Error 500 Internal Server Error
             return ResponseEntity
                     .internalServerError()
                     .build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Fee> addFee(@RequestBody Fee fee) {
        try {
            return feeService.service(fee, false, null);
        } catch (Exception e) {
            // Error 500 Internal Server Error
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fee> updateFee(@RequestBody Fee update, @PathVariable Long id) {
        try {
            return feeService.service(update, true, id);
        } catch (Exception e) {
            // Error 500 Internal Server Error
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
