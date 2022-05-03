/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.repository;

import com.gt.interpackage.reports.model.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
    
    @Query(value = "SELECT * FROM invoice e WHERE e.nit = ?1", nativeQuery = true)
    List<Invoice> getInvoicesByClient(Integer nit);
}
