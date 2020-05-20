package cat.marianao.daw2.m07.uf3.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;

@Stateless
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
	entityManager.persist(user);
    }

    @Override
    public void edit(User user) {
	entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
	user = entityManager.merge(user);
	entityManager.remove(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllActiveUsers() {
	return entityManager.createQuery("select u from User u where u.active = true").getResultList();
    }

    @Override
    public void deactivateUser(User user) {
	entityManager.createQuery("update User u set u.active = false where u.userId = :id")
		.setParameter("id", user.getUserId()).executeUpdate();
    }

    @Override
    public User findUserByUsername(String username) {
	try {
	    return (User) entityManager.createQuery("select u from User u where u.username = :username")
		    .setParameter("username", username).getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

    @Override
    public User findUserByEmail(String email) {
	try {
	    return (User) entityManager.createQuery("select u from User u where u.email = :email")
		    .setParameter("email", email).getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

}
