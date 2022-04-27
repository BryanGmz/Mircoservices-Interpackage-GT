/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.controller;

import com.gt.interpackage.receptionist.service.InvoiceService;
import com.gt.interpackage.receptionist.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gt.interpackage.receptionist.model.Invoice;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_RECEP + "/invoice")
public class InvoiceController {
    
    @Autowired
    private InvoiceService _invoiceService;
    
    @PostMapping("/")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
        try {
            return _invoiceService.createInvoice(invoice);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/client/{nit}")
    public ResponseEntity<List<Invoice>> getInvoicesByClient(@PathVariable Integer nit){
        return ResponseEntity.ok(_invoiceService.getInvoicesByClient(nit));
    }
    
}
