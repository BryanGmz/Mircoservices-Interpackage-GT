/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.model.Package;
import com.gt.interpackage.receptionist.repository.PackageRepository;
import java.awt.print.Pageable;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis
 */
@Service
public class PackageService {
    
    @Autowired
    private PackageRepository _packageRepository;
    
    /*
     * Metodo que llama al repositorio de paquetes para obtener
     * los datos del paquete que se recibe como parametro
    */
    public Package getById(Long id){
        return _packageRepository.getById(id);
    }
    
//    /*
//     * Metodo que llama al repositorio de paquetes para obtener una paginacion 
//     * con todos los paquetes que se encuentran en destino y no han sido retirados.
//    */
//    public Page<Package> getAllAtDestination(Pageable pageable){
//        return _packageRepository.findAllByAtDestinationTrueAndRetiredFalse(pageable);
//    }
//
//    /*
//     * Metodo que llama al repositorio de paquetes para obtener una paginacion 
//     * con todos los paquetes que se encuentran en ruta.
//    */
//    public Page<Package> getAllOnRoute(Pageable pageable){
//        return _packageRepository.findAllByOnWayTrue(pageable);
//    }
    
    /*
     * Metodo que llama al repositorio de paquetes para agregar
     * un nuevo paquete a la bse de datos
    */
    public Package addPackage(Package pack){
        return _packageRepository.save(pack);
    }
    
    /*
     * Metodo que llama al repositorio de paquetes para actualizar
     * un paquete especifico
    */
    public <S extends Package> Package update(S entity, Long id) throws Exception {
        Package pack = getById(id);
        if(pack != null) {
            pack.setDescription(entity.getDescription());
            pack.setOnWay(entity.getOnWay());
            pack.setRetired(entity.getRetired());
            pack.setAtDestination(entity.getAtDestination());
            pack.setUnitTotal(entity.getUnitTotal());
            pack.setRoute(entity.getRoute());
            pack.setWeight(entity.getWeight());
            return _packageRepository.save(pack);
        } return null;        
    }
    
//    /**
//     * Metodo que hace uso del repositorio de paquetes para obtener 
//     * un listado de paquetes cuyo id de factura sea igual al parametro que se
//     * recibe.
//     * @param id
//     * @return 
//     */
//    public List<Package> getPackagesOnRouteByInoviceId(Long id){
//        return _packageRepository.findAllByInvoiceIdAndOnWayTrue(id);
//    }
    
    public List<Package> getPackagesByInvoice(Long id_invoice){
        return _packageRepository.getPackagesByInvoice(id_invoice);
    }
    
}
