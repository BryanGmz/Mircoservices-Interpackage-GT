package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Destination;
import com.gt.interpackage.administration.repository.DestinationRepository;
import com.gt.interpackage.administration.source.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private RouteService routeService;
    
    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }

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

    public Page<Destination> getAll(int page, int size){
        return destinationRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public List<Destination> findByName(String name){
        return destinationRepository.findByNameStartingWith(name);
    }

    public Destination getDestinationById(Long id) throws BadRequestException {
        Optional<Destination> destination = destinationRepository.findById(id);
        if(destination.isEmpty())
            throw  new BadRequestException("El destino no existe en el sistema.");
        return destination.get();
    }

    public Destination update(Destination destination) throws BadRequestException{
        this.validateDestinationName(destination);
        Destination updatedDestination = this.getDestinationById(destination.getId());
        updatedDestination.setName(destination.getName());
        updatedDestination.setDescription(destination.getDescription());
        updatedDestination.setFee(destination.getFee());
        return destinationRepository.save(updatedDestination);
    }

    public void delete(Long id) throws BadRequestException{
        if(routeService.routeHasDestinationAssigned(id))
            throw  new BadRequestException("No se puede eliminar el destino ya que se encuentra asignado a rutas.");

        Destination tempDestination = this.getDestinationById(id);
        destinationRepository.delete(tempDestination);
    }

    public boolean exists(String name, Long id){
        return destinationRepository.existsDestinationByNameAndIdIsNot(name, id);
    }

    private void validateDestinationName(Destination destination) throws BadRequestException{
        if(destination.getName().isBlank() || destination.getName().isEmpty() )
            throw new BadRequestException("Nombre de destino no valido");

        if(this.exists(destination.getName(), destination.getId()))
            throw new BadRequestException("Nombre de destino ya registrado en el sistema");
    }

}
