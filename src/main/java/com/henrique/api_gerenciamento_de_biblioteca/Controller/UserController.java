package com.henrique.api_gerenciamento_de_biblioteca.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.henrique.api_gerenciamento_de_biblioteca.DTO.UserDTO;
import com.henrique.api_gerenciamento_de_biblioteca.Model.UserModel;
import com.henrique.api_gerenciamento_de_biblioteca.Service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/cadastro")
    public UserModel createUser(@RequestBody UserDTO dto) {
        UserModel user = userService.createUser(dto);
        return user;
    }
}   
