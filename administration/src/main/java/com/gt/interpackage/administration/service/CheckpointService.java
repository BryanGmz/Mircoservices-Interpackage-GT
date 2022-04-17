package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Checkpoint;
import com.gt.interpackage.administration.model.Employee;
import com.gt.interpackage.administration.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeTypeService employeeTypeService;

    @Autowired
    private RouteService routeService;

    public Checkpoint getCheckpointById(Long id){
        try {
            Checkpoint checkpoint = checkpointRepository.getById(id);
            if (checkpoint == null) return null;
            if (checkpoint.getDescription() != null) { }
            return checkpoint;
        } catch (EntityNotFoundException e) {
            return null;
        }

    }

    public ResponseEntity<Checkpoint> updateCheckpoint(Checkpoint update, Long id, boolean isOperatorUpdated) throws Exception {
        Checkpoint checkpoint = getCheckpointById(id);
        Employee operator;
        if (checkpoint == null) {
            return ResponseEntity.notFound().build();
        }
        if (isOperatorUpdated) {
            try {
                operator = employeeService.getByCUI(update.getAssignedOperator().getCUI());
                if (operator == null) {
                    return new ResponseEntity ("El operador seleccionado no existe en el sistema.", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception ex) {
                return ResponseEntity.internalServerError().build();
            }
            update.setAssignedOperator(operator);
        } else {
            update.setAssignedOperator(checkpoint.getAssignedOperator());
        }
        update.setActive(checkpoint.getActive());
        update.setRoute(checkpoint.getRoute());
        return execute(update, 1, id, update.getDescription().equalsIgnoreCase(checkpoint.getDescription()));
    }

    private ResponseEntity<Checkpoint> execute(Checkpoint checkpoint, Integer type, Long id, boolean check) throws Exception {

        if(checkpoint.getDescription() == null || checkpoint.getQueueCapacity() == null || checkpoint.getAssignedOperator().getCUI() == null
                || checkpoint.getActive() == null || checkpoint.getRoute().getId() == null || checkpoint.getOperationFee() == null)
            return new ResponseEntity("Todos los campos son obligatorios.", HttpStatus.BAD_REQUEST);

        if(checkpoint.getDescription().isEmpty() || checkpoint.getDescription().isBlank())
            return new ResponseEntity("Nombre de punto de control no valido.", HttpStatus.BAD_REQUEST);
        if(!check && checkpointRepository.existsCheckpointByRouteIdAndDescription(checkpoint.getRoute().getId(), checkpoint.getDescription()))
            return new ResponseEntity("Nombre de punto de control ya registrado en la ruta seleccionada.", HttpStatus.BAD_REQUEST);
        if(!routeService.existsById(checkpoint.getRoute().getId()))
            return new ResponseEntity("La ruta seleccionada no existe en el sistema.", HttpStatus.BAD_REQUEST);

        Employee employee = employeeService.getByCUI(checkpoint.getAssignedOperator().getCUI());

        if(employee == null)
            return new ResponseEntity("El operador seleccionada no existe en el sistema.", HttpStatus.BAD_REQUEST);

        if(employeeTypeService.getEmployeeTypeByName("operator").getId() != Long.parseLong(employee.getType().toString()))
            return new ResponseEntity("El empleado seleccionada no es un operador.", HttpStatus.BAD_REQUEST);

        //if(type == RequestType.SAVE){
        if(type == 0) {
            System.out.println(checkpoint.getOperationFee());
            //Checkpoint tempCheckpoint = checkpointService.create(checkpoint);
            Checkpoint tempCheckpoint = checkpointRepository.save(checkpoint);
            return ResponseEntity.created(new URI("/checkpoint/"+tempCheckpoint.getId())).body(tempCheckpoint);
        //} else if (type == RequestType.UPDATE){
        } else if (type == 1) {
            Checkpoint tempCheckpoint = checkpointRepository.save(checkpoint);
            return  ResponseEntity.ok(tempCheckpoint);
        }

        //Agregar la parte de actualizacion y retornar el valor correspodiente
        return null;
    }
}
