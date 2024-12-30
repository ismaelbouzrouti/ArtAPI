package org.enterpriseapp.artapi.users;

import org.enterpriseapp.artapi.Imapper;
import org.enterpriseapp.artapi.exceptions.EmailAlreadyExistsException;
import org.enterpriseapp.artapi.exceptions.UserNameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Imapper<User,UserDTO> {

    @Autowired
    UserRepository repository;

    public void saveUser(User user){

        if(repository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException("A user with this email already exists");

        } else if (repository.existsByUserName(user.getUserName())) {

            throw new UserNameAlreadyExistsException("A user with this username already exists");
        }

       else try{
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
