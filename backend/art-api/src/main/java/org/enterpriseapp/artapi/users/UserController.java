package org.enterpriseapp.artapi.users;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO dto){

        if (dto == null){

            return ResponseEntity.badRequest().body("invalid user data");
        }

        try {
            service.saveUser(service.convertToEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created");
        }catch (Exception e){

          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user");
        }



    }


}
