package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public <S extends Destination> S save (S entity) {
        if (destinationRepository.existsDestinationByName(entity.getName())) return null;
        return destinationRepository.save(entity);
    }

    public ResponseEntity<Destination> service(Destination destination, boolean update, Long id) throws URISyntaxException, Exception {
        if (destination != null) {
            if (destination.getFee() == null || destination.getName() == null || destination.getName().isEmpty() || destination.getName().isBlank()) {
                return new ResponseEntity("Todos los campos son obligatorios.", HttpStatus.BAD_REQUEST);
            } else {
                if (destination.getFee() < 0) {
                    return new ResponseEntity("La tarifa debe de ser mayor a 0.", HttpStatus.BAD_REQUEST);
                } else {
                    if (update) {
                        // Agregar metodo para el update
                        return null;
                    } else {
                        Destination savedDestination = save(destination);
                        return savedDestination == null
                                ?
                                new ResponseEntity("Ya existe un destino con el nombre: " + destination.getName(), HttpStatus.BAD_REQUEST)
                                : ResponseEntity
                                .created (
                                        new URI("/destination/" + savedDestination.getId()))
                                .body(savedDestination);
                    }
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
