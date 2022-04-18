package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getByCUI(Long CUI) throws Exception {
        try {
            Employee employee = employeeRepository.getById(CUI);
            if (employee == null) return null;
            if(employee.getName() != null){ }
            return employee;
        } catch(EntityNotFoundException e){
            return null;
        }
    }
}
