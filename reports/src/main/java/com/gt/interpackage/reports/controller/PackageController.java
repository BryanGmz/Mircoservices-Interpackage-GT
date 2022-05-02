/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.controller;

import com.gt.interpackage.reports.model.Package;
import com.gt.interpackage.reports.service.PackageService;
import com.gt.interpackage.reports.source.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONTEND,allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_REPORTS + "/package")
public class PackageController {
    
    @Autowired
    private PackageService _packageService;

    @GetMapping("/invoice/{id_invoice}")
    public ResponseEntity<List<Package>> getPackagesByInvoice(@PathVariable Long id_invoice){
        return ResponseEntity.ok(_packageService.getPackagesByInvoice(id_invoice));
    }
}
