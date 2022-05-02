/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.service;

import com.gt.interpackage.reports.model.Invoice;
import com.gt.interpackage.reports.repository.InvoiceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis
 */
@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository _invoiceRepository;
    
     public List<Invoice> getInvoicesByClient(Integer nit){
        return _invoiceRepository.getInvoicesByClient(nit);
    }
       
}
