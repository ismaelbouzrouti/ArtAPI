package org.enterpriseapp.artapi.users;

import org.enterpriseapp.artapi.Imapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Imapper<User,UserDTO> {

    @Autowired
    UserRepository repository;

    public void saveUser(User user){

        try{
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                dto.getPassword(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName()
        );
    }

    @Override
    public UserDTO convertToDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }
}
