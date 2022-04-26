package com.gt.interpackage.administration.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false, name ="date_emit")
    private LocalDate dateEmit;

    @Column (nullable = false, scale = 2)
    private Double total;

    @Column (nullable = false)
    private Integer nit;

    public Invoice() { }

    public Invoice(Long id, LocalDate dateEmit, Double total, Integer nit) {
        this.id = id;
        this.dateEmit = dateEmit;
        this.total = total;
        this.nit = nit;
    }

    public Invoice(LocalDate dateEmit, Double total, Integer nit) {
        this.dateEmit = dateEmit;
        this.total = total;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEmit() {
        return dateEmit;
    }

    public void setDateEmit(LocalDate dateEmit) {
        this.dateEmit = dateEmit;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getNit() {
        return nit;
    }

    public void setNit(Integer nit) {
        this.nit = nit;
    }
}
