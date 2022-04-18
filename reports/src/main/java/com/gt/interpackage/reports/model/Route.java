package com.gt.interpackage.reports.model;

import javax.persistence.*;

@Entity
@Table (name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false, length = 75)
    private String name;

    @Column (nullable = false, name = "packages_on_route")
    private Integer packagesOnRoute;

    @Column (nullable = false, name = "total_packages")
    private Integer totalPackages;

    @Column (nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn (nullable = false, name = "id_destination")
    private Destination destination;

    public Route() { }

    public Route(String name, Integer packagesOnRoute, Integer totalPackages, Boolean active, Destination destination) {
        this.name = name;
        this.packagesOnRoute = packagesOnRoute;
        this.totalPackages = totalPackages;
        this.active = active;
        this.destination = destination;
    }

    public Route(Long id, String name, Integer packagesOnRoute, Integer totalPackages, Boolean active, Destination destination) {
        this.id = id;
        this.name = name;
        this.packagesOnRoute = packagesOnRoute;
        this.totalPackages = totalPackages;
        this.active = active;
        this.destination = destination;
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

    public Integer getPackagesOnRoute() {
        return packagesOnRoute;
    }

    public void setPackagesOnRoute(Integer packagesOnRoute) {
        this.packagesOnRoute = packagesOnRoute;
    }

    public Integer getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(Integer totalPackages) {
        this.totalPackages = totalPackages;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
