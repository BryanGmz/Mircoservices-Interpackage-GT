/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gt.interpackage.receptionist.model.Client;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Luis
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

    @Query(value = "SELECT * FROM client e WHERE e.nit = ?1", nativeQuery = true)
    Client findByNit(Integer nit);
    
//    @Query(value = "SELECT * FROM client e JOIN invoice i ON e.nit = i.nit", nativeQuery = true)
//    List<ClientReport> clientDetails();

    public boolean existsClientByCui(Long CUI);
    
    public boolean existsClientByNit(Integer nit);

}
