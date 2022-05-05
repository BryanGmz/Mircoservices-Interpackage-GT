package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public Package getById(Long id)   {
        try {
            return  packageRepository.getById(id);
        } catch(EntityNotFoundException e){
            return null;
        }
    }

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
            return packageRepository.save(pack);
        } return null;
    }
}
