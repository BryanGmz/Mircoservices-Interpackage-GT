/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gt.interpackage.receptionist.model.Invoice;
import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Luis
 */
@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository _invoiceRepository;
    
    public ResponseEntity<Invoice> createInvoice(Invoice invoice){
        try {
             Invoice savedInvoice = _invoiceRepository.save(invoice);
             return ResponseEntity.created(
                    new URI("/invoice/"+savedInvoice.getId()))
                    .body(savedInvoice);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    public List<Invoice> getInvoicesByClient(Integer nit){
        return _invoiceRepository.getInvoicesByClient(nit);
    }
    
}
