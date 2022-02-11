package com.example.spring_boot.service;

import com.example.spring_boot.dao.UserRepository;
import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(int id, User updatedUser) {
        User userDB = userRepository.findById(id).get();
        if(Objects.nonNull(updatedUser.getUsername())) {
            userDB.setName(updatedUser.getName());
        }
        if(Objects.nonNull(updatedUser.getLastName())) {
            userDB.setLastName(updatedUser.getLastName());
        }
        userDB.setAge(updatedUser.getAge());
        userDB.setLastName(updatedUser.getLastName());
        userDB.setPassword(updatedUser.getPassword());
        userDB.setRoles(updatedUser.getRoles());
        userRepository.save(updatedUser);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {
       return userRepository.findByName(name);
    }
}
