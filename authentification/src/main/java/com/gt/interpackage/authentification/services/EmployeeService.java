/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.authentification.services;

import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public <S extends Employee> S save(S entity){
        try {
            return employeeRepository.save(entity);
        } catch(Exception e){
            return null;
        }
    }
    
    public Employee getByCUI(Long cui) throws Exception {
        return employeeRepository.getById(cui);
    } 

}
