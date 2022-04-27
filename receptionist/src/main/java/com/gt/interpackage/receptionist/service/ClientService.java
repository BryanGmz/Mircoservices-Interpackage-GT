/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.repository.ClientRepository;
import com.gt.interpackage.receptionist.model.Client;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luis
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository _clientRepository;
    
    public List<Client> findAll(){
        return _clientRepository.findAll();
    }
    
    public <S extends Client> S save(S entity){
        return _clientRepository.save(entity);
    }
    
    public Client getByNit(Integer nit) throws Exception{
        try {
            Client client = _clientRepository.findByNit(nit);
            return client;
        } catch(EntityNotFoundException e){
            return null;
        }
    }
    
    
    
    public ResponseEntity<Client> createClient(Client client){
        try {
            if(existsByCui(client.getCui()))
                return new ResponseEntity("El cliente con el id: "+client.getCui()+" ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            if(existsByNit(client.getNit()))
                return new ResponseEntity("El cliente con el numero de NIT: "+client.getNit()+" ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            Client savedClient = save(client);
            return ResponseEntity.created(
                new URI("/client/" + savedClient.getCui()))
                .body(savedClient);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    public boolean existsByCui(Long cui){
        return _clientRepository.existsClientByCui(cui);
    }
    
    public boolean existsByNit(Integer nit){
        return _clientRepository.existsClientByNit(nit);
    }
    
    
}
