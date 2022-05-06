package com.gt.interpackage.administration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table (name = "checkpoint")
@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Checkpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column (name ="operation_fee", nullable = false)
    private Double operationFee;

    @Column (name = "queue_capacity", nullable = false)
    private Integer queueCapacity;

    @Column (name = "packages_on_queue", nullable = false)
    private Integer packagesOnQueue;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn (name="assigned_operator", nullable = false)
    private Employee assignedOperator;

    @ManyToOne
    @JoinColumn (name="id_route", nullable = false)
    private Route route;

    public Checkpoint() { }

    public Checkpoint(String description, Double operationFee, Integer queueCapacity, Integer packagesOnQueue, Boolean active, Employee assignedOperator, Route route) {
        this.description = description;
        this.operationFee = operationFee;
        this.queueCapacity = queueCapacity;
        this.packagesOnQueue = packagesOnQueue;
        this.active = active;
        this.assignedOperator = assignedOperator;
        this.route = route;
    }

    public Checkpoint(Long id, String description, Double operationFee, Integer queueCapacity, Integer packagesOnQueue, Boolean active, Employee assignedOperator, Route route) {
        this.id = id;
        this.description = description;
        this.operationFee = operationFee;
        this.queueCapacity = queueCapacity;
        this.packagesOnQueue = packagesOnQueue;
        this.active = active;
        this.assignedOperator = assignedOperator;
        this.route = route;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getOperationFee() {
        return operationFee;
    }

    public void setOperationFee(Double operationFee) {
        this.operationFee = operationFee;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getPackagesOnQueue() {
        return packagesOnQueue;
    }

    public void setPackagesOnQueue(Integer packagesOnQueue) {
        this.packagesOnQueue = packagesOnQueue;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Employee getAssignedOperator() {
        return assignedOperator;
    }

    public void setAssignedOperator(Employee assignedOperator) {
        this.assignedOperator = assignedOperator;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
