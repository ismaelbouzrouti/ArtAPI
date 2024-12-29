package org.enterpriseapp.artapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/signup")
    public void createUser(@RequestBody UserDTO dto){

        if (dto!= null){
            System.out.println(dto.toString());
            System.out.println(service.convertToEntity(dto).toString());
            service.saveUser(service.convertToEntity(dto));
        }


    }


}
