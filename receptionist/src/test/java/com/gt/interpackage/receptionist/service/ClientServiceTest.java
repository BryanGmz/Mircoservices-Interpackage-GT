/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.service;

import com.gt.interpackage.receptionist.model.Client;
import com.gt.interpackage.receptionist.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author Luis
 */
public class ClientServiceTest {

    @Mock
    private ClientRepository _clientRepository;
    
    @InjectMocks
    private ClientService _clientService;
    
    private Client client;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        client = new Client(1888L, "Jose Manuel", "Garcia", 22, 5555, "Quetzaltenango");      
    }
    
    @Test
    public void testFindAll(){
        Mockito.when(_clientRepository.findAll())
                .thenReturn(Arrays.asList(client));
        assertNotNull(_clientService.findAll());
    }
    
    @Test
    public void testGetByNit() throws Exception {
        Mockito.when(_clientRepository.findByNit(ArgumentMatchers.any(Integer.class)))
                .thenReturn(client);
        Client searched = _clientService.getByNit(5555);
        Assertions.assertThat(searched.getCui()).isEqualTo(1888L);
    }
    
    @Test
    public void testSave(){
        Mockito.when(_clientRepository.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(client);
        Client clientSaved = _clientService.save(new Client("Juan Manuel", "Maldonado", 30, 7778, "Guatemala"));
        assertNotNull(clientSaved);
        assertEquals(clientSaved.getNit(), 5555);
        Mockito.verify(_clientRepository).save(ArgumentMatchers.any(Client.class));
    }
    
    @Test
    public void testExistsByCui() throws Exception {
        Mockito.when(
                _clientRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(client);
        Mockito.when(
                _clientRepository.existsClientByCui(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        boolean exist = _clientService.existsByCui(1888L);
        assertNotNull(exist);
        assertEquals(exist, true);
        Mockito.verify(_clientRepository).existsClientByCui(ArgumentMatchers.any(Long.class));
    }
    
    @Test
    public void testExistsByNit() throws Exception {
        Mockito.when(
                _clientRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(client);
        Mockito.when(
                _clientRepository.existsClientByNit(ArgumentMatchers.any(Integer.class)))
                .thenReturn(true);
        boolean exist = _clientService.existsByNit(5555);
        assertNotNull(exist);
        assertEquals(exist, true);
        Mockito.verify(_clientRepository).existsClientByNit(ArgumentMatchers.any(Integer.class));
    }
    

    
    
    @Test
    public void testCreateClientException() throws Exception {
        Mockito.when(
            _clientService.existsByCui(ArgumentMatchers.any(Long.class)))
                .thenThrow(new Exception());
        ResponseEntity responseEntity = _clientService.createClient(client);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @Test
    public void testCreateClientCUIExists() throws Exception {
        Mockito.when(
                _clientRepository.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(client);
        Mockito.when(
                _clientService.existsByCui(ArgumentMatchers.any(Long.class)))
                .thenReturn(true);
        ResponseEntity responseEntity = _clientService.createClient(client);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testCreateClientNITExists() throws Exception{
        Mockito.when(
                _clientRepository.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(client);
        Mockito.when(
                _clientService.existsByNit(ArgumentMatchers.any(Integer.class)))
                .thenReturn(true);
        ResponseEntity responseEntity = _clientService.createClient(client);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Test
    public void testCreateClientSuccesfully(){
        Mockito.when(
                _clientRepository.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(client);
        ResponseEntity responseEntity = _clientService.createClient(client);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(_clientRepository).save(ArgumentMatchers.any(Client.class));
    }
    
    
}
