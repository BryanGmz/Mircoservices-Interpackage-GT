package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.repository.EmployeeRepository;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTypeService employeeTypeService;

    public ResponseEntity<Page<Employee>> findAll(Pageable pageable){
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return new ResponseEntity<Page<Employee>>(employees, HttpStatus.OK);
    }
    
    public List<Employee> findAllActivates(){
        return employeeRepository.findAll();
    }
    
    public List<Employee> findAllDeactivates(){
        return employeeRepository.getAllDeactivates();
    }
    
    public List<Employee> findAllActivatesNotAdmin(){
        return employeeRepository.getAllActivatesNotAdmin();
    }
    
    public <S extends Employee> S save(S entity){
        try {
            return employeeRepository.save(entity);
        } catch(Exception e){
            return null;
        }
    }
    
    
    public ResponseEntity<Employee> update(Employee entity, Long CUI) throws Exception{
        Employee emp = getByCUI(CUI);
        if(emp != null){
            return ResponseEntity.ok(update2(entity, CUI));
        }
        return new ResponseEntity("Error, Usuario con CUI "+CUI+" no encontrado", HttpStatus.BAD_REQUEST);
    }

    public <S extends Employee> Employee update2(S entity, Long id) throws Exception{
        Employee emp = getByCUI(id);
        if(emp != null){
            emp.setEmail(entity.getEmail());
            emp.setType(entity.getType());
            emp.setLastname(entity.getLastname());
            emp.setName(entity.getName());
            emp.setPassword(entity.getPassword());
            emp.setActivo(entity.getActivo());
            return employeeRepository.save(emp);
        }
        return null;
    }

    
    public ResponseEntity<Employee> createEmployee(Employee emp){
        try {
            if(exists(emp.getUsername()))
                return new ResponseEntity("El usuario con el id: "+emp.getUsername()+" ya existe", HttpStatus.BAD_REQUEST);
            if(existsByCUI(emp.getCUI()))
                return new ResponseEntity("El usuario con el CUI: "+emp.getCUI()+" ya existe", HttpStatus.BAD_REQUEST);
            Employee empSaved = save(emp);
            return ResponseEntity.created(
                    new URI("/employee/"+empSaved.getCUI()))
                    .body(empSaved);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    public boolean exists(String username){
        return employeeRepository.existsEmployeeByUsername(username);
    }
    
    public boolean existsByCUI(Long CUI){
        return employeeRepository.existsEmployeeByCUI(CUI);
    }
    
    
    

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
