package cat.marianao.daw2.m07.uf3.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;

public class UserServiceImpl implements UserService {
    private EntityManager entityManager;

    public UserServiceImpl(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    @Override
    public void create(User user) {
	entityManager.persist(user);
    }

    @Override
    public User edit(User user) {
	return entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
	entityManager.remove(user);
    }

    @Override
    public User findUserByUsername(String username) {
	return (User) entityManager.createQuery("SELECT object(o) FROM User o WHERE o.username = :username")
		.setParameter("username", username).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findActiveUsers() {
	return entityManager.createQuery("SELECT object(o) FROM User o WHERE o.active = true").getResultList();
    }

    @Override
    public User findHighestRankUser() {
	return (User) entityManager.createQuery("SELECT object(o) FROM User o ORDER BY o.rank DESC").setMaxResults(1)
		.getSingleResult();
    }
}
