package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.model.Fee;
import com.gt.interpackage.administration.repository.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GT-Xela", "De Guate a Xela", 15.50);
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
}
