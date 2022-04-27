package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.repository.PackageCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageCheckpointService {

    @Autowired
    private PackageCheckpointRepository packageCheckpointRepository;

    public boolean existsAnyRegisterOfCheckpointById(Long id){
        return packageCheckpointRepository.existsPackageCheckpointByCheckpointId(id);
    }

}
