/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.reports.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis
 */
@Entity
@Table (name = "package")
public class Package {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @Column (name = "on_way", nullable = false)
    private Boolean onWay;
    
    @Column (name = "at_destination", nullable = false)
    private Boolean atDestination;
    
    @Column (nullable = false)
    private Boolean retired;
    
    @Column (scale = 2, nullable = false)
    private Double weight;
        
    @Column (name = "unit_total", nullable = false)
    private Double unitTotal;
    
    @Column (nullable = false)
    private Boolean priority;
    
    @Column (columnDefinition = "TEXT", nullable = true)
    private String description;
    
    @Column (nullable = true, name="start_date")
    private LocalDate dateStart;

    @Column (nullable = true, name="end_date")
    private LocalDate dateEnd;
    
    @ManyToOne
    @JoinColumn (nullable = true, name="route")
    private Route route;
    
    @ManyToOne
    @JoinColumn (name = "id_invoice", nullable = false)
    private Invoice invoice;
    
    @ManyToOne
    @JoinColumn (name = "id_destination", nullable = false)
    private Destination destination;

    public Package() { }
    
    public Package(Long id, Boolean onWay, Boolean atDestination, Boolean retired, Double weight, Double subTotal, Boolean priority, String description, Invoice invoice, Double unitTotal, Route route, Destination destination) {
        this.id = id;
        this.onWay = onWay;
        this.atDestination = atDestination;
        this.retired = retired;
        this.weight = weight;
        this.unitTotal = unitTotal;
        this.priority = priority;
        this.description = description;
        this.invoice = invoice;
        this.route = route;
        this.destination = destination;
    }

    public Package(Boolean onWay, Boolean atDestination, Boolean retired, Double weight, Double subTotal, Boolean priority, String description, Invoice invoice, Double unitTotal, Route route, Destination destination) {
        this.onWay = onWay;
        this.atDestination = atDestination;
        this.retired = retired;
        this.weight = weight;
        this.unitTotal = unitTotal;
        this.priority = priority;
        this.description = description;
        this.invoice = invoice;
        this.route = route;
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOnWay() {
        return onWay;
    }

    public void setOnWay(Boolean onWay) {
        this.onWay = onWay;
    }

    public Boolean getAtDestination() {
        return atDestination;
    }

    public void setAtDestination(Boolean atDestination) {
        this.atDestination = atDestination;
    }

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getUnitTotal() {
        return unitTotal;
    }

    public void setUnitTotal(Double unitTotal) {
        this.unitTotal = unitTotal;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
    
}
