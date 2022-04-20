/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.authentification.dto;

/**
 *
 * @author bryan
 */
public class ChangePasswordDTO {
    
    private String password;
    private String confirmPassword;
    private String tokenPassword;

    public ChangePasswordDTO() { }

    public ChangePasswordDTO(String password, String confirmPassword, String tokenPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.tokenPassword = tokenPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }
    
    public boolean isValidateFields(){
        return (this.password != null && this.confirmPassword != null && this.tokenPassword != null
                && !this.confirmPassword.isBlank() && !this.confirmPassword.isEmpty()
                && !this.password.isBlank() && !this.password.isEmpty()
                && !this.tokenPassword.isBlank() && !this.tokenPassword.isEmpty());
    }
    
}
