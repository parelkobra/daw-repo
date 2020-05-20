/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.institutmarianao.service;

 
import es.institutmarianao.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
 
 
public interface UserService {
     
    public User getUserByUsername(String username) throws UsernameNotFoundException;
    public boolean addUser(User user);
    public void updateUser(User user);
}