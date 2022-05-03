/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.service;

import com.gt.interpackage.reports.model.Package;
import com.gt.interpackage.reports.repository.PackageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis
 */
@Service
public class PackageService {

    @Autowired
    private PackageRepository _packageRepository;
    
    public List<Package> getPackagesByInvoice(Long id_invoice){
        return _packageRepository.getPackagesByInvoice(id_invoice);
    }    
}
