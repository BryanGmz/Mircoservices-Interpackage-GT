/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.repository;

import com.gt.interpackage.receptionist.model.Package;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luis
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, Long>{
    
    @Query(value = "SELECT * FROM package p WHERE p.at_destination = true AND p.retired = false", nativeQuery = true)
    List<Package> getInDestination(); 
    
    @Query(value = "SELECT * FROM package WHERE p.id_invoice = ?1", nativeQuery = true)
    List<Package> getPackagesByInvoice(Long id_invoice);

    public Page<Package> findAllByAtDestinationTrueAndRetiredFalse(Pageable pageable);

    public Page<Package> findAllByOnWayTrue(Pageable pageable);

    public List<Package> findAllByInvoiceIdAndOnWayTrue(Long id);
}
