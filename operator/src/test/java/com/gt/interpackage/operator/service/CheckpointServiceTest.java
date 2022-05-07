package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Checkpoint;
import com.gt.interpackage.operator.repository.CheckpointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointServiceTest {

    @Mock
    private CheckpointRepository checkpointRepository;

    @InjectMocks
    private  CheckpointService checkpointService;

    private Checkpoint checkpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        checkpoint = new Checkpoint(1L, "", 50D, 0,0,true, null, null);
    }

    @Test
    void create() {
        Mockito.when(checkpointRepository.save(checkpoint)).thenReturn(checkpoint);
        assertEquals(checkpoint.getId(), checkpointService.create(checkpoint).getId());
    }

    @Test
    void getAllByAssignedOperator() {
        long cui = 1111111111;
        Mockito.when(checkpointRepository.findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(cui)).thenReturn(List.of(checkpoint));
        assertEquals(1, checkpointService.getAllByAssignedOperator(cui).size());
        assertEquals(checkpoint.getId(), checkpointService.getAllByAssignedOperator(cui).get(0).getId());
    }

    @Test
    void getCheckpointById() throws com.gt.interpackage.administration.source.BadRequestException {
        Mockito.when(checkpointRepository.findById(checkpoint.getId())).thenReturn(Optional.of(checkpoint));
        assertEquals(checkpoint.getId(), checkpointService.getCheckpointById(checkpoint.getId()).getId());
    }

    @Test
    void getCheckpointByIdNotExistCheckpoint() {
        Exception exception = null;
        Mockito.when(checkpointRepository.findById(checkpoint.getId())).thenReturn(Optional.empty());
        try{
            checkpointService.getCheckpointById(checkpoint.getId());
        } catch (Exception e){
            exception = e;
        }
        assertEquals("El punto de control no existe en el sistema.", exception.getMessage());
    }
}