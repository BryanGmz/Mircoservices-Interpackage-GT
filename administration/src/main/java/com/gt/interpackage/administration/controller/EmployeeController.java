package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.service.EmployeeService;
import com.gt.interpackage.administration.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping(Constants.API_V1_ADMIN + "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value ="/search-by-cui/{cui}")
    public ResponseEntity<List<Employee>> getOperatorsByCUI(@PathVariable String cui){
        try{
            return  ResponseEntity.ok(employeeService.getAllOperatorsByCUI(cui).get());
        } catch(Exception e){
            return new ResponseEntity("Error en el servidor.\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
