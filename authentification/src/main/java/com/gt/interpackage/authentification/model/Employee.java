package com.gt.interpackage.authentification.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "employee")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Employee {

    @Id
    private Long CUI;

    @Column(nullable = false, length = 75)
    private String name;

    @Column (nullable = false, length = 75)
    private String lastname;

    @Column (nullable = false, length = 75)
    private String username;

    @Column (nullable = false, length = 200)
    private String email;

    @Column (nullable = false)
    private Integer type;

    @Column (nullable = true)
    private String tokenPassword;

    @Column (nullable = false, length = 60)
    private String password;

    @Column (nullable = false)
    private Boolean activo;

    public Employee() { }

    public Employee(Long CUI, String name, String lastname, Integer type, String password, String email, String username, Boolean activo) {
        this.CUI = CUI;
        this.name = name;
        this.lastname = lastname;
        this.type = type;
        this.password = password;
        this.username = username;
        this.email = email;
        this.activo = activo;
    }

    public Employee(String name, String lastname, Integer type, String password, String email, String username, Boolean activo) {
        this.name = name;
        this.lastname = lastname;
        this.type = type;
        this.password = password;
        this.username = username;
        this.email = email;
        this.activo = activo;
    }

    public Long getCUI() {
        return CUI;
    }

    public void setCUI(Long CUI) {
        this.CUI = CUI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
