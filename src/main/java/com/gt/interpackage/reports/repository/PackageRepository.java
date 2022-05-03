/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.repository;

import com.gt.interpackage.reports.model.Package;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, Long>{

    @Query(value = "SELECT * FROM package p WHERE p.id_invoice = ?1", nativeQuery = true)
    List<Package> getPackagesByInvoice(Long id_invoice);
    
}
