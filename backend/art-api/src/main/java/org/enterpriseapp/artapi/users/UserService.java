package org.enterpriseapp.artapi.users;

import org.enterpriseapp.artapi.mapper.Imapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Imapper<User,UserDTO> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user){

        try{
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user to the database",e);
        }


    }

    public void updateUser(User user){

        if(user != null && repository.existsById(user.getId())){

            repository.save(user);
        }
    }

    public void deleteUser(Long id){

        if(id != null && repository.existsById((id))){

            repository.deleteById(id);
        }
    }



    @Override
    public User convertToEntity(UserDTO dto) {

        return new User(
                dto.getUserName(),
                dto.getId(),
                passwordEncoder.encode( dto.getPassword()),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName()
        );
    }

    @Override
    public UserDTO convertToDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }
}
