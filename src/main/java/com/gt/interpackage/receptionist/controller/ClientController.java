/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.controller;

import com.gt.interpackage.receptionist.service.ClientService;
import com.gt.interpackage.receptionist.source.Constants;
import com.gt.interpackage.receptionist.model.Client;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luis
 */
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping (Constants.API_V1_RECEP + "/client")
public class ClientController {
    
    @Autowired
    private ClientService _clientService;
    
    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.ok(_clientService.findAll());
    }
    
    @GetMapping("/{nit}")
    public ResponseEntity<Client> getClientByNit(@PathVariable Integer nit){
        try {
            Client client = _clientService.getByNit(nit);
            return client != null ?
                    ResponseEntity.ok(client) :
                    ResponseEntity.notFound()
                        .build();
        } catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping ("/")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        try {
            return _clientService.createClient(client);
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
