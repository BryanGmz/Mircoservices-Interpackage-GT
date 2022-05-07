package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.*;
import com.gt.interpackage.administration.repository.CheckpointRepository;
import com.gt.interpackage.administration.source.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CheckpointServiceTest {

    @Mock
    private CheckpointRepository checkpointRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeTypeService employeeTypeService;

    @Mock
    private PackageCheckpointService packageCheckpointService;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private CheckpointService checkpointService;

    private Exception exception;

    private Checkpoint checkpoint;
    private Employee employee;
    private EmployeeType employeeType;
    private Destination destination;
    private Route route;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeType = new EmployeeType(2L, "operator", "Operador");
        employee = new Employee(1234678L, "Juan", "Gonzales", "juanito", "juanito@gmail.com", 2, null, "12345678", true);
        destination = new Destination(1L, "GT-Xela", "De Guate a Xela", 15.50);
        route = new Route(1L, "Ruta 1", 15, 35, true, destination);
        checkpoint = new Checkpoint(1L, "Punto de control 1", 15.50, 25, 12, true, employee, route);
    }

    @Test
    public void testGetCheckpointById() {
        Mockito.when(
                checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Checkpoint getCheckpoint = checkpointService.getCheckpointById(1L);
        assertNotNull(getCheckpoint);
        assertEquals(checkpoint.getDescription(), "Punto de control 1");
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointNotFoundForUpdate() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointNotFoundForUpdateEntityNotFoundException() throws Exception  {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenThrow(new EntityNotFoundException());
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testOperatorNotFoundForCheckpointUpdate() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, true);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testOperatorNotFoundForCheckpointUpdateException() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenThrow(new Exception());
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, true);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateButRequiredFieldsAreMissing() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        checkpoint = new Checkpoint(1L, "Punto de control 1", null, null, 12, true, employee, route);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateButNameIsInvalid() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        checkpoint = new Checkpoint(1L, "", 15.50, 35, 12, true, employee, route);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateButNameAlreadyExists() throws Exception {
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(true);
        Checkpoint checkpointUpdate = new Checkpoint(1L, "Punto de control 2", 15.50, 35, 12, true, employee, route);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpointUpdate, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointRepository).existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class));
    }

    @Test
    public void testCheckpointUpdateButRouteDoesNotExist() throws Exception{
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                        routeService.existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(false);
        checkpoint.setOperationFee(15.50);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateButEmployeeDoesNotExist() throws Exception{
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                        routeService.existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        Mockito.when(
                        employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        checkpoint.setOperationFee(15.50);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateButEmployeeIsBNotAnOperator() throws Exception{
        employee.setType(1);
        checkpoint.setOperationFee(15.50);
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                        routeService.existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        Mockito.when(
                        employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(employee);
        Mockito.when(
                        employeeTypeService.getEmployeeTypeByName(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeType);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testCheckpointUpdateSuccessful() throws Exception {
        checkpoint.setOperationFee(15.50);
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                        routeService.existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        Mockito.when(
                        employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(employee);
        Mockito.when(
                        employeeTypeService.getEmployeeTypeByName(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeType);
        Mockito.when(
                        checkpointRepository.save(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(checkpoint);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, false);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(employeeService).getByCUI(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointRepository).save(ArgumentMatchers.any(Checkpoint.class));
    }

    @Test
    public void testCheckpointOperatorUpdateSuccessful() throws Exception {
        checkpoint.setAssignedOperator(employee);
        Mockito.when(
                        checkpointRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                        employeeService.getByCUI(ArgumentMatchers.any(Long.class)))
                .thenReturn(employee);
        Mockito.when(
                        checkpointRepository.existsCheckpointByRouteIdAndDescription(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(String.class)))
                .thenReturn(false);
        Mockito.when(
                        routeService.existsById(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        Mockito.when(
                        employeeTypeService.getEmployeeTypeByName(ArgumentMatchers.any(String.class)))
                .thenReturn(employeeType);
        Mockito.when(
                        checkpointRepository.save(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(checkpoint);
        ResponseEntity responseEntity = checkpointService.updateCheckpoint(checkpoint, 1L, true);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Mockito.verify(checkpointRepository).getById(ArgumentMatchers.any(Long.class));
        Mockito.verify(routeService).existsById(ArgumentMatchers.any(Long.class));
        Mockito.verify(checkpointRepository).save(ArgumentMatchers.any(Checkpoint.class));
    }

    @Test
    public void testGetAllCheckpointsByDestinationId() {
        Mockito.when(
                checkpointRepository.findAllByRoute_Destination_IdAndRoute_ActiveAndActiveOrderById(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Boolean.class), ArgumentMatchers.any(Boolean.class)))
                .thenReturn(Arrays.asList(checkpoint));
        List<Checkpoint> list = checkpointService.getAllCheckpointsByDestinationId(1L);
        assertNotNull(list);
        assertEquals(list.size(), 1);
    }
<<<<<<< HEAD

    @Test
    public void testGetCheckpointById2() throws BadRequestException {
       Mockito.doReturn(Optional.of(checkpoint)).when(checkpointRepository).findById(checkpoint.getId());
       Checkpoint requstedCheckpoint = checkpointService.getCheckpointById2(checkpoint.getId());
       assertEquals(checkpoint.getId(), requstedCheckpoint.getId());
    }

    @Test
    public void testGetCheckpointById2NotExist() throws BadRequestException {
        exception = null;
        Mockito.doReturn(Optional.empty()).when(checkpointRepository).findById(checkpoint.getId());
        try{
            Checkpoint requstedCheckpoint = checkpointService.getCheckpointById2(checkpoint.getId());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("El punto de control no existe en el sistema.", exception.getMessage());
    }

    @Test
    public void testDeletePackagesOnQueue(){
        Checkpoint tempCheckpoint = new Checkpoint(1L, "", 0D, 0, 1, true, null, null);
        Mockito.doReturn(Optional.of(tempCheckpoint)).when(checkpointRepository).findById(tempCheckpoint.getId());
        try{
            checkpointService.delete(tempCheckpoint.getId());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("No se puede eliminar un punto de control que contiene paquetes en cola.", exception.getMessage());
    }

    @Test
    public void testDeleteNoRegisterOnPackageCheckpoint(){
        Checkpoint tempCheckpoint = new Checkpoint(1L, "", 0D, 0, 0, true, null, null);
        Mockito.doReturn(Optional.of(tempCheckpoint)).when(checkpointRepository).findById(tempCheckpoint.getId());
        Mockito.doReturn(true).when(packageCheckpointService).existsAnyRegisterOfCheckpointById(tempCheckpoint.getId());
        try{
            checkpointService.delete(tempCheckpoint.getId());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("No se puede eliminar el punto de control debido a que se han registrado paquetes en el mismo anteriormente. Consulte al DBA.", exception.getMessage());
    }

    @Test
    public void testDelete() throws BadRequestException {
        Checkpoint tempCheckpoint = new Checkpoint(1L, "", 0D, 0, 0, true, null, null);
        Mockito.doReturn(Optional.of(tempCheckpoint)).when(checkpointRepository).findById(tempCheckpoint.getId());
        Mockito.doReturn(false).when(packageCheckpointService).existsAnyRegisterOfCheckpointById(tempCheckpoint.getId());
        checkpointService.delete(tempCheckpoint.getId());
  }
}
