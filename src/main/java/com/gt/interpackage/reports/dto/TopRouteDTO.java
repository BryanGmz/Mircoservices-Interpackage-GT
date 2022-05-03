package com.gt.interpackage.reports.dto;


public class TopRouteDTO {

    private Integer quantity;
    private String route;
    private String destination;

    public TopRouteDTO() { }

    public TopRouteDTO(Integer quantity, String route, String destination) {
        this.quantity = quantity;
        this.route = route;
        this.destination = destination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
