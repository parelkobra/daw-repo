package es.institutmarianao.domain.repository.impl;

import es.institutmarianao.domain.Role;
import es.institutmarianao.domain.User;
import es.institutmarianao.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final Set<User> listOfUsers = new TreeSet<User>();

    public InMemoryUserRepository() {
        User user = new User();
        user.setUsername("user@email.com");
        user.setPassword("1234");
        user.setAuthorities(getDefaultUserRoles());
        user.setFirstName("User");
        listOfUsers.add(user);
    }

    public User getUserByUsername(String username) {
        for (User user : listOfUsers) {
            if (user != null && user.getUsername() != null
                    && user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private List<Role> getDefaultUserRoles() {
        Role r = new Role();
        r.setName("ROLE_USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(r);
        return roles;
    }

    public boolean addUser(User user) {
        user.setAuthorities(getDefaultUserRoles());
        return listOfUsers.add(user);
    }

    public void updateUser(User user) {
        if (listOfUsers.contains(user)) {
            listOfUsers.remove(user);
        }
        user.setAuthorities(getDefaultUserRoles());
        listOfUsers.add(user);
    }
}
