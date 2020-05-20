/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.institutmarianao.service.impl;

import es.institutmarianao.domain.User;
import es.institutmarianao.domain.repository.UserRepository;
import es.institutmarianao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
     public User getUserByUsername(final String username) {
            User user = userRepository.getUserByUsername(username);
            return user;
        }

    public boolean addUser(User user) {
        return userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
