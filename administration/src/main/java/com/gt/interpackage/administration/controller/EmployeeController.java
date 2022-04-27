/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.service.EmployeeService;
import com.gt.interpackage.administration.service.EmployeeTypeService;
import com.gt.interpackage.administration.source.Constants;
import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.model.EmployeeType;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

/**
 *
 * @author Luis
 */
@RestController
@RequestMapping (Constants.API_V1_ADMIN + "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService _employeeService;
    
    @Autowired
    private EmployeeTypeService _empTypService;
    
    @GetMapping("/")
    public ResponseEntity<Page<Employee>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return _employeeService.findAll(PageRequest.of(page, size, Sort.by("name")));
    }
    
    @GetMapping("/actives/")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(_employeeService.findAllActivates());
    }
    
    @GetMapping("/actives/not-admin/")
    public ResponseEntity<List<Employee>> getAllEmployeesNotAdmin(){
        return ResponseEntity.ok(_employeeService.findAllActivatesNotAdmin());
    }
    
    
    @GetMapping("/deactivates/")
    public ResponseEntity<List<Employee>> getAllDeactivatesEmployess(){
        return ResponseEntity.ok(_employeeService.findAllDeactivates());
    }
    
    @PostMapping ("/")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp){
        try{
            return _employeeService.createEmployee(emp);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();//404 Bad Request
        }
    }

    @PutMapping ("/{cui}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee empUpdate, @PathVariable Long cui){
        try {
            return _employeeService.update(empUpdate, cui);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
//      /**
//     * Metodo que realiza una llamada al servicio de empleados para obtener todos los 
//     * operadores cuyo cui inicie con el nombre que se recibe como parametro.
//     * @param cui
//     * @return Listado de rutas encontradas | Error 400
//     */
//    @CrossOrigin
//    @GetMapping(value ="/search-by-cui/{cui}")
//    public ResponseEntity<List<Employee>> getOperatorsByCUI(@PathVariable String cui){
//        try{
//            Long operatorTypeId = employeeTypeService.getEmployeeTypeByName("operator").getId();
//            Optional<List<Employee>> operators = _employeeService.getAllOperatorsByCUI(cui, Integer.valueOf(operatorTypeId.toString()));
//            return  ResponseEntity.ok(operators.get());   
//        } catch(Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//    
//    @GetMapping (value = "/search-by-email/{email}")
//    public ResponseEntity<Employee> getUserByEmail(@PathVariable String email) {
//        try {
//            Employee employee = _employeeService.getUserByUsernameOrEmail(email);
//            return employee != null ? 
//                    ResponseEntity.ok(employee) :   // 200 OK 
//                    ResponseEntity                  // 404 Not Found
//                        .notFound()
//                        .header("Error", "No se encuentra registrado un empleado con el correo electrónico: " + email)
//                        .build();
//        } catch (Exception ex) {
//            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
//        }
//    }
//    
//    @PostMapping ("/change-password")
//    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
//        if (changePasswordDTO.isValidateFields()) {
//            if (!changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmPassword())) {
//                return ResponseEntity
//                        .badRequest()
//                        .header("errorMessage", "Las contraseñas no coinciden")
//                        .build();
//            } else {
//                try {
//                    Employee employee = _employeeService.getUserByTokenPassword(changePasswordDTO.getTokenPassword());
//                    if (employee == null) {
//                        return ResponseEntity                  // 404 Not Found
//                            .notFound()
//                            .header("errorMessage", "No se encuentra registrado un empleado con las credenciales indicadas.")
//                            .build();
//                    } else {
//                        employee.setPassword(changePasswordDTO.getPassword());
//                        employee.setTokenPassword(null);
//                        _employeeService.save(employee);
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("message", "Contraseña actualizada con exito.");
//                        return ResponseEntity.ok(map);
//                    }
//                } catch (Exception e) {
//                     return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
//                }
//            }
//        } else {
//            return ResponseEntity
//                    .badRequest()
//                    .header("errorMessage", "Todos los camopos son obligatorios.")
//                    .build();
//        }
//    }
}
