package com.gt.interpackage.authentification.service;

import com.gt.interpackage.authentification.dto.ChangePasswordDTO;
import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

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

    public Employee getUserByUsernameOrEmail(String usernameOrEmail) throws Exception {
        try {
            Employee employee = employeeRepository.getByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (employee == null) return null;
            if (employee.getName() != null) { }
            return employee;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public Employee getUserByTokenPassword(String tokenPassword) throws Exception {
        try {
            Employee employee = employeeRepository.findByTokenPassword(tokenPassword);
            if (employee == null) return null;
            if (employee.getName() != null) { }
            return employee;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.isValidateFields()) {
            if (!changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmPassword())) {
                return new ResponseEntity("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
            } else {
                try {
                    Employee employee = getUserByTokenPassword(changePasswordDTO.getTokenPassword());
                    if (employee == null) {
                        // Error 404 Not Found
                        return ResponseEntity
                                .notFound()
                                .build();
                    } else {
                        employee.setPassword(changePasswordDTO.getPassword());
                        employee.setTokenPassword(null);
                        save(employee);
                        Map<String, Object> map = new HashMap<>();
                        map.put("message", "Contraseña actualizada con exito.");
                        return ResponseEntity.ok(map);
                    }
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
                }
            }
        } else {
            return new ResponseEntity("Todos los camopos son obligatorios.", HttpStatus.BAD_REQUEST);
        }
    }
}
