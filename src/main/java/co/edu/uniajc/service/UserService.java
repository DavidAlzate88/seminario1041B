package co.edu.uniajc.service;

import co.edu.uniajc.exception.UserException;
import co.edu.uniajc.model.User;
import co.edu.uniajc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserException("El email ya está en uso");
        }
    }

    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            throw new UserException("Error retrieving user with email " + email, e);
        }
    }

    public User findByName(String name) {
        try {
            return userRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            throw new UserException("Error retrieving user with name " + name, e);
        }
    }

    public List<User> findUsersByRoleName(String roleName) {
        try {
            return userRepository.findUsersByRoleName(roleName);
        } catch (Exception e) {
            throw new UserException("Error retrieving users with role " + roleName, e);
        }
    }

    public List<User> findUsersByCreationDate(Date creationDate) {
        try {
            return userRepository.findByCreationDate(creationDate);
        } catch (Exception e) {
            throw new UserException("Error retrieving users with creation date " + creationDate, e);
        }
    }

    public User updateUserRoles(Long userId, List<Role> roles) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setRoles(roles);
                return userRepository.save(user);
            }
            return null;
        } catch (Exception e){
            throw new UserException("Error Updating User Roles", e);
        }
    }
}
