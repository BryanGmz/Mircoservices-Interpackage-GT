package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTypeService employeeTypeService;

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

    public Optional<List<Employee>> getAllOperatorsByCUI(String cui){
        Long operatorTypeId = employeeTypeService.getEmployeeTypeByName("operator").getId();
        return employeeRepository.findByCuiContainsAndEmployeeTypeIs(cui, Integer.parseInt(operatorTypeId.toString()));
    }
}
