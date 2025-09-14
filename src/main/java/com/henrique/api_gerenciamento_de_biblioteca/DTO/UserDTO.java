package com.henrique.api_gerenciamento_de_biblioteca.DTO;

import com.henrique.api_gerenciamento_de_biblioteca.Enum.UserRoleEnum;

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private UserRoleEnum role;
    
    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
    
    

}
