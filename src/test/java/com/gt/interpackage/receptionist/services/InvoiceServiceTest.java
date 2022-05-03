/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.services;

import com.gt.interpackage.receptionist.model.Invoice;
import com.gt.interpackage.receptionist.service.InvoiceService;
import com.gt.interpackage.receptionist.repository.InvoiceRepository;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import java.util.Arrays;
/**
 *
 * @author Luis
 */
public class InvoiceServiceTest {
 
    @Mock
    private InvoiceRepository _invoiceRepository;
    
    @InjectMocks
    private InvoiceService _invoiceService;
 
    private Invoice invoice;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2020, 05, 02);
        invoice = new Invoice(1L, date, 50.0, 555);      
    }

    @Test
    public void testCreateInvoiceSuccesfully(){
        Mockito.when(
                _invoiceRepository.save(ArgumentMatchers.any(Invoice.class)))
                .thenReturn(invoice);
        ResponseEntity responseEntity = _invoiceService.createInvoice(invoice);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(_invoiceRepository).save(ArgumentMatchers.any(Invoice.class));
    }
    
    @Test
    public void testGetInvoicesByClient(){
        Mockito.when(_invoiceRepository.getInvoicesByClient(ArgumentMatchers.any(Integer.class)))
                .thenReturn(Arrays.asList(invoice));
        assertNotNull(_invoiceService.getInvoicesByClient(555));
    }
    
    
}
