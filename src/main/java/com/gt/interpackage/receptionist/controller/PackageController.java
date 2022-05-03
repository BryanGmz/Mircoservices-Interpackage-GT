/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.controller;

import com.gt.interpackage.receptionist.model.Package;
import com.gt.interpackage.receptionist.service.PackageService;
import com.gt.interpackage.receptionist.source.Constants;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_RECEP + "/package")
public class PackageController {
    
    @Autowired
    private PackageService _packageService;
    
    @PostMapping("/")
    public ResponseEntity<Package> createPackage(@RequestBody Package pack){
        try {
            Package tempPackage = _packageService.addPackage(pack);
            return ResponseEntity.created(new URI("/package/"+tempPackage.getId()))
                    .body(tempPackage);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@RequestBody Package packUpdate, @PathVariable Long id){
        try {
            Package pack = _packageService.update(packUpdate, id);
            return pack != null ?
                    ResponseEntity.ok(pack) :
                    ResponseEntity.notFound()
                        .build();
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/invoice/{id_invoice}")
    public ResponseEntity<List<Package>> getPackagesByInvoice(@PathVariable Long id_invoice){
        return ResponseEntity.ok(_packageService.getPackagesByInvoice(id_invoice));
    }

    @GetMapping("/in-destination/")
    public ResponseEntity<Page<Package>> getInDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(_packageService.getAllAtDestination(page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/on-route")
    public ResponseEntity<Page<Package>> getOnRoute(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(_packageService.getAllOnRoute(page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("trace-by-invoice/{id}")
    public ResponseEntity<List<Package>> getPackagesOnRouteByInvoiceId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(_packageService.getPackagesOnRouteByInoviceId(id));
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
