package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.EmployeeType;
import com.gt.interpackage.administration.repository.EmployeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeTypeService {

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    public EmployeeType getEmployeeTypeByName(String name){
        return employeeTypeRepository.findByName(name).get();
    }

    public EmployeeType create(EmployeeType employeeType){
        return employeeTypeRepository.save(employeeType);
    }

    public void deleteAllTypes(){
        if(employeeTypeRepository.count() > 0)
            employeeTypeRepository.deleteAll();
    }
}
