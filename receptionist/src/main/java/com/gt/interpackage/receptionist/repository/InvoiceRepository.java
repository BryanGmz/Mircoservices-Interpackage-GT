/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gt.interpackage.receptionist.model.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Luis
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT * FROM invoice e WHERE e.nit = ?1", nativeQuery = true)
    List<Invoice> getInvoicesByClient(Integer nit);
}
