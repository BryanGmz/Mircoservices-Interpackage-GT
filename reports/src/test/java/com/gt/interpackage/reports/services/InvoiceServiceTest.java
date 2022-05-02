/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.services;

import com.gt.interpackage.reports.model.Invoice;
import com.gt.interpackage.reports.service.InvoiceService;
import com.gt.interpackage.reports.repository.InvoiceRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testGetInvoicesByClient(){
        Mockito.when(_invoiceRepository.getInvoicesByClient(ArgumentMatchers.any(Integer.class)))
                .thenReturn(Arrays.asList(invoice));
        assertNotNull(_invoiceService.getInvoicesByClient(555));
    }
    
}
