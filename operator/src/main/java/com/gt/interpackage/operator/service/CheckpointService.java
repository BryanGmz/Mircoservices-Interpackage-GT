package com.gt.interpackage.operator.service;


import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.operator.model.Checkpoint;
import com.gt.interpackage.operator.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    public Checkpoint create(Checkpoint checkpoint){
        return checkpointRepository.save(checkpoint);
    }

    public List<Checkpoint> getAllByAssignedOperator(Long cui){
        return checkpointRepository.findAllByAssignedOperatorCUIAndActiveTrueOrderByRouteId(cui);
    }

    public Checkpoint getCheckpointById(Long id) throws BadRequestException{
        Optional<Checkpoint> checkpoint = checkpointRepository.findById(id);
        if(checkpoint.isEmpty())
            throw new BadRequestException("El punto de control no existe en el sistema.");
        return checkpoint.get();
    }

    public List<Checkpoint> getAllCheckpointsByDestinationId(Long idDestination) {
        return checkpointRepository.findAllByRoute_Destination_IdAndRoute_ActiveAndActiveOrderById(idDestination, true, true);
    }
}
