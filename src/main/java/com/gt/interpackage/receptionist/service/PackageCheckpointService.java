package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.model.PackageCheckpoint;
import com.gt.interpackage.receptionist.repository.PackageCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class PackageCheckpointService {

    @Autowired
    private PackageCheckpointRepository packageCheckpointRepository;

    public PackageCheckpoint getPackageCheckpoint(Long id){
        return packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(id);
    }

    public String getTimeOnRouteByPackageId(Long id){
        return packageCheckpointRepository.getTimeOnRouteByPackageId(id);
    }

    public PackageCheckpoint getPackageCheckpointWithTimeOnRoute(Long id){
        PackageCheckpoint tempPackageCheckpoint = this.getPackageCheckpoint(id);

        String timeOnRoute = this.getTimeOnRouteByPackageId(id);
        if(timeOnRoute != null)
            tempPackageCheckpoint.setTimeOnCheckpoint(Time.valueOf(timeOnRoute) );
        else
            tempPackageCheckpoint.setTimeOnCheckpoint(new Time(00,00,00));

        return tempPackageCheckpoint;
    }

}
