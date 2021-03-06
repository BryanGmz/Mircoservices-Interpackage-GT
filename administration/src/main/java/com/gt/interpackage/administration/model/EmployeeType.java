package com.gt.interpackage.administration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "employee_type")
public class EmployeeType {

    @Id
    private Long id;

    @Column(nullable = false, length = 75)
    private String name;

    @Column (nullable = true, columnDefinition = "TEXT")
    private String description;

    public EmployeeType() { }

    public EmployeeType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public EmployeeType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
