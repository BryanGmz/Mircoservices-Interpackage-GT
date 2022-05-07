package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.repository.DestinationRepository;
import com.gt.interpackage.administration.source.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private RouteService routeService;

    @Mock
    private Page<Destination> destinationPage;

    @InjectMocks
    private DestinationService destinationService;


    private Destination destination;
    private Exception exception;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GT-Xela", "De Guate a Xela", 15.50);
    }
    
    @Test
    public void testFindAll(){
        Mockito.when(
                destinationRepository.findAll())
                .thenReturn(Arrays.asList(destination));
        List<Destination> list = destinationService.findAll();
        assertNotNull(list);
        assertEquals(list.size(), 1);
    }

    @Test
    public void testSaveDestination() {
        Mockito.when(
                        destinationRepository.save(ArgumentMatchers.any(Destination.class)))
                .thenReturn(destination);
        Destination save = destinationService.save(new Destination("GT-Xela", "De Guate a Xela", 15.50));
        assertNotNull(save);
        assertEquals(save.getName(), destination.getName());
        Mockito.verify(destinationRepository).save(ArgumentMatchers.any(Destination.class));
    }

    @Test
    public void textADestinationWithTheSameNameAlreadyExists() {
        Mockito.when(
                destinationRepository.existsDestinationByName(ArgumentMatchers.any(String.class)))
                .thenReturn(Boolean.TRUE);
        Destination save = destinationService.save(new Destination("GT-Xela", "De Guate a Xela", 15.50));
        assertNull(save);
        Mockito.verify(destinationRepository).existsDestinationByName(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testNullDestinationObjectWasSent() throws Exception {
        ResponseEntity responseEntity = destinationService.service(null, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAllRequiredFieldsWereSubmitted() throws Exception {
        ResponseEntity responseEntity = destinationService.service(new Destination(), false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTheDestinationFieldIsLessThanZero() throws Exception {
        destination.setFee(-15.50);
        ResponseEntity responseEntity = destinationService.service(destination, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDestinationCreated() throws Exception {
        Mockito.when(
                destinationRepository.existsDestinationByName(ArgumentMatchers.any(String.class)))
                .thenReturn(Boolean.FALSE);
        Mockito.when(
                destinationRepository.save(ArgumentMatchers.any(Destination.class)))
                .thenReturn(destination);
        ResponseEntity responseEntity = destinationService.service(destination, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(destinationRepository).existsDestinationByName(ArgumentMatchers.any(String.class));
        Mockito.verify(destinationRepository).save(ArgumentMatchers.any(Destination.class));
    }

    @Test
    public void testNotCreatedBecauseADestinationWithTheSameNameAlreadyExists() throws Exception {
        Mockito.when(
                        destinationRepository.existsDestinationByName(ArgumentMatchers.any(String.class)))
                .thenReturn(Boolean.TRUE);
        ResponseEntity responseEntity = destinationService.service(destination, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Mockito.verify(destinationRepository).existsDestinationByName(ArgumentMatchers.any(String.class));
    }

    @Test
    public void testFindAllDestination() {
        Mockito.when(
                destinationRepository.findAll())
                .thenReturn(Arrays.asList(destination));
        List<Destination> list = destinationService.findAll();
        assertNotNull(list);
        assertEquals(list.size(), 1);
        Mockito.verify(destinationRepository).findAll();
    }

    @Test
    public void testGetDestinationByIdExist() throws BadRequestException {
        Mockito.doReturn(Optional.of(destination)).when(destinationRepository).findById(destination.getId());
        Destination requestDestination = destinationService.getDestinationById(destination.getId());
        assertEquals(destination.getId(), requestDestination.getId());
    }

    @Test
    public void testGetDestinationByIdNoExist() throws BadRequestException {
        exception = null;
        try{
            destinationService.getDestinationById(1L);
        } catch (Exception e){
            exception = e;
        }
        assertEquals(exception.getMessage(), "El destino no existe en el sistema.");
    }

    @Test
    public void testFindByName(){
        Mockito.when(destinationRepository.findByNameStartingWith(destination.getName())).thenReturn(Arrays.asList(destination));
        List<Destination> requestList = destinationService.findByName(destination.getName());

        assertEquals(1, requestList.size());
        assertEquals(destination.getId(), requestList.get(0).getId());
    }

    @Test
    public void testValidateDestinationNoValidName(){
        exception = null;
        try{
            destinationService.validateDestinationName(new Destination("", "", 0D));
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de destino no valido", exception.getMessage());
    }

    @Test
    public void testValidateDestinationAlreadyExistName(){
        exception = null;
        Mockito.doReturn(true).when(destinationRepository).existsDestinationByNameAndIdIsNot("A", 1L);
        try{
            destinationService.validateDestinationName(new Destination(1L,"A", "", 0D));
        } catch (Exception e){
            exception = e;
        }
        assertEquals("Nombre de destino ya registrado en el sistema", exception.getMessage());
    }

    @Test
    public void testNoDelete(){
        exception = null;
        Mockito.doReturn(true).when(routeService).routeHasDestinationAssigned(destination.getId());
        try{
            destinationService.delete(destination.getId());
        } catch(Exception e){
            exception = e;
        }
        assertEquals("No se puede eliminar el destino ya que se encuentra asignado a rutas.",  exception.getMessage());
    }

    @Test
    public void testDelete() throws BadRequestException {
        Mockito.doReturn(false).when(routeService).routeHasDestinationAssigned(destination.getId());
        Mockito.doReturn(Optional.of(destination)).when(destinationRepository).findById(destination.getId());
        destinationService.delete(destination.getId());
    }

    @Test
    public void testUpdate() throws BadRequestException {
        Destination tempDestination = new Destination(4L, "Guatemala", "Guatemala", 10D);
        Mockito.doReturn(Optional.of(tempDestination)).when(destinationRepository).findById(tempDestination.getId());
        Mockito.when(destinationRepository.save(ArgumentMatchers.any(Destination.class))).thenReturn(tempDestination);

        Destination updatedDestination = destinationService.update(tempDestination);
        assertEquals("Guatemala", updatedDestination.getName());
    }

}
