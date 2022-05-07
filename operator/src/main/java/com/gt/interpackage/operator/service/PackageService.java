package com.gt.interpackage.operator.service;

import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.operator.model.Package;
import com.gt.interpackage.operator.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public Package getById(Long id) throws BadRequestException {
        try {
            return  packageRepository.getById(id);
        } catch(EntityNotFoundException e){
            throw new BadRequestException("Paquete no encontrado en el sistema.");
        }
    }

    public Package update(Package packages, Long id) throws Exception {
        Package pack = getById(id);
        pack.setDescription(packages.getDescription());
        pack.setOnWay(packages.getOnWay());
        pack.setRetired(packages.getRetired());
        pack.setAtDestination(packages.getAtDestination());
        pack.setUnitTotal(packages.getUnitTotal());
        pack.setRoute(packages.getRoute());
        pack.setWeight(packages.getWeight());
        return packageRepository.save(pack);
    }
}
