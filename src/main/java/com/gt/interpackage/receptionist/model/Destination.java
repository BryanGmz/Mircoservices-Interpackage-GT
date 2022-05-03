/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.receptionist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Luis
 */
@Entity
@Table (name = "destination")
public class Destination {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @Column (nullable = false, length = 75)
    private String name;
    
    @Column (columnDefinition = "TEXT", nullable = true)
    private String description;
    
    @Column (scale = 2, nullable = false)
    private Double fee;
    
    public Destination(){}

    public Destination(String name, String description, Double fee) {
        this.name = name;
        this.description = description;
        this.fee = fee;
    }
    
    public Destination(Long id, String name, String description, Double fee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
   
}
