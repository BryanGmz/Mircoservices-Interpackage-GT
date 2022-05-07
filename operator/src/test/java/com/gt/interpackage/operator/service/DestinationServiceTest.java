package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Destination;
import com.gt.interpackage.operator.repository.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private Destination destination;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "Destino", "Descripcion", 50D);
    }

    @Test
    void findAll() {
        Mockito.when(destinationRepository.findAll()).thenReturn(List.of(destination));
        assertEquals(1, destinationService.findAll().size());
        assertEquals(destination.getId(), destinationService.findAll().get(0).getId());
    }
}