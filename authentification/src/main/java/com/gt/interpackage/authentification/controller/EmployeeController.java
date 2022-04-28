package com.gt.interpackage.authentification.controller;

import com.gt.interpackage.authentification.dto.ChangePasswordDTO;
import com.gt.interpackage.authentification.model.Employee;
import com.gt.interpackage.authentification.service.EmployeeService;
import com.gt.interpackage.authentification.source.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_AUTH + "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/search-by-email/{email}")
    public ResponseEntity<Employee> getUserByEmail(@PathVariable String email) {
        try {
            Employee employee = employeeService.getUserByUsernameOrEmail(email);
            return employee != null ?
                    ResponseEntity.ok(employee) :   // 200 OK
                    ResponseEntity                  // 404 Not Found
                            .notFound()
                            .build();
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .build(); // 500 Internal Server Error
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        return employeeService.changePassword(changePasswordDTO);
    }

}
