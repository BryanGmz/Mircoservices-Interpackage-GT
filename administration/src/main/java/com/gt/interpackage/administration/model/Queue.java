package com.gt.interpackage.administration.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "queue")
public class Queue {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long queue;

    @Column (nullable = false)
    private Integer position;

    @ManyToOne
    @JoinColumn (name = "id_package", nullable = false)
    private Package packages;

    public Queue() { }

    public Queue (Long queue, Package packages, Integer position) {
        this.queue = queue;
        this.packages = packages;
        this.position = position;
    }

    public Long getQueue() {
        return queue;
    }

    public void setQueue(Long queue) {
        this.queue = queue;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Package getPackages() {
        return packages;
    }

    public void setPackages(Package packages) {
        this.packages = packages;
    }
}
