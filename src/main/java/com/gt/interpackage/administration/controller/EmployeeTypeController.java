package com.gt.interpackage.administration.controller;

import com.gt.interpackage.administration.model.EmployeeType;
import com.gt.interpackage.administration.service.EmployeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeTypeController {

    @Autowired
    private EmployeeTypeService employeeTypeService;

    /**
     * Metodo que se ejecuta al momento de iniciar la aplicacion.
     * Establece y hace uso del servicio de tipos de empleado para
     * crear los tipos de empleados de la aplicacion.
     * @param event
     */
    @EventListener
    public void insertEmployeeTypes(ApplicationReadyEvent event){
        employeeTypeService.deleteAllTypes();
        employeeTypeService.create( new EmployeeType(1L, "administrator", "Administrador"));
        employeeTypeService.create( new EmployeeType(2L, "operator", "Operador"));
        employeeTypeService.create( new EmployeeType(3L, "receptionist", "Recepcionista"));
        employeeTypeService.create( new EmployeeType(4L, "hola", "nomaspaver"));
    }
}
