package cat.marianao.daw2.m07.uf3.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.impl.UserServiceImpl;

public class UserServiceTest {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private UserService userService;

    @Before
    public void setUp() {
	entityManager = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit").createEntityManager();
	userService = new UserServiceImpl(entityManager);
	entityTransaction = entityManager.getTransaction();
    }

    @After
    public void cleanUp() {
	entityManager.close();
    }

    @Test
    public void findAllUsers() {
	String username = "jdoe";
	User user = new User();
	user.setActive(true);
	user.setCreatedOn(new Timestamp(new Date().getTime()));
	user.setEmail("test@test.com");
	user.setName("Jane");
	user.setPassword("password");
	user.setRank(100);
	user.setUsername(username);
	User user1 = new User();
	user1.setActive(true);
	user1.setCreatedOn(new Timestamp(new Date().getTime()));
	user1.setEmail("test1@test.com");
	user1.setName("Joe");
	user1.setPassword("password");
	user1.setRank(100);
	user1.setUsername("joeTest");

	entityTransaction.begin();
	userService.create(user);
	userService.create(user1);
	entityTransaction.commit();

	User userFromDB = userService.findUserByUsername(username);
	Assert.assertNotNull(userFromDB);
	Assert.assertEquals("jdoe", userFromDB.getUsername());
	Assert.assertEquals("test@test.com", userFromDB.getEmail());
	Assert.assertNotNull(userFromDB.getUserId());
    }

    @Test
    public void editUser() {
	String username = "george";
	User user = new User();
	user.setActive(true);
	user.setCreatedOn(new Timestamp(new Date().getTime()));
	user.setEmail("test1@test.com");
	user.setName("George");
	user.setPassword("password");
	user.setRank(100);
	user.setUsername("george");

	entityTransaction.begin();
	userService.create(user);
	entityTransaction.commit();

	Assert.assertNotNull(user);

	username = "Paco";
	user.setUsername(username);

	entityTransaction.begin();
	userService.edit(user);
	entityTransaction.commit();

	User userFromDB = userService.findUserByUsername(username);
	Assert.assertNotNull(userFromDB);
	Assert.assertEquals("Paco", username);
    }

    @Test(expected = NoResultException.class)
    public void removeUser() {
	String username = "steve";
	User user = new User();
	user.setActive(true);
	user.setCreatedOn(new Timestamp(new Date().getTime()));
	user.setEmail("steve@test.com");
	user.setName("Steve");
	user.setPassword("password");
	user.setRank(100);
	user.setUsername(username);

	entityTransaction.begin();
	userService.create(user);
	entityTransaction.commit();

	Assert.assertNotNull(user);

	entityTransaction.begin();
	userService.remove(user);
	entityTransaction.commit();

	// This will throw NoResultException
	userService.findUserByUsername(username);
    }

    @Test
    public void findActiveUsers() {
	User user1 = new User();
	user1.setActive(true);
	user1.setCreatedOn(new Timestamp(new Date().getTime()));
	user1.setEmail("alex@test.com");
	user1.setName("Alex");
	user1.setPassword("password");
	user1.setRank(100);
	user1.setUsername("alex");

	User user2 = new User();
	user2.setActive(false);
	user2.setCreatedOn(new Timestamp(new Date().getTime()));
	user2.setEmail("sara@test.com");
	user2.setName("Sara");
	user2.setPassword("password");
	user2.setRank(100);
	user2.setUsername("sara");

	User user3 = new User();
	user3.setActive(true);
	user3.setCreatedOn(new Timestamp(new Date().getTime()));
	user3.setEmail("mike@test.com");
	user3.setName("Mike");
	user3.setPassword("password");
	user3.setRank(100);
	user3.setUsername("mike");

	entityTransaction.begin();
	userService.create(user1);
	userService.create(user2);
	userService.create(user3);
	entityTransaction.commit();

	List<User> activeUsers = userService.findActiveUsers();

	Assert.assertNotNull(activeUsers);
	Assert.assertEquals(2, activeUsers.size());
    }

    @Test
    public void findHighestRankUser() {
	User user1 = new User();
	user1.setActive(true);
	user1.setCreatedOn(new Timestamp(new Date().getTime()));
	user1.setEmail("teo@test.com");
	user1.setName("Teo");
	user1.setPassword("password");
	user1.setRank(120);
	user1.setUsername("teo");

	User user2 = new User();
	user2.setActive(false);
	user2.setCreatedOn(new Timestamp(new Date().getTime()));
	user2.setEmail("ivan@test.com");
	user2.setName("Ivan");
	user2.setPassword("password");
	user2.setRank(120);
	user2.setUsername("ivan");

	User user3 = new User();
	user3.setActive(true);
	user3.setCreatedOn(new Timestamp(new Date().getTime()));
	user3.setEmail("laura@test.com");
	user3.setName("Laura");
	user3.setPassword("password");
	user3.setRank(40);
	user3.setUsername("laura");

	entityTransaction.begin();
	userService.create(user1);
	userService.create(user2);
	userService.create(user3);
	entityTransaction.commit();

	User highestRankUser = userService.findHighestRankUser();

	Assert.assertNotNull(highestRankUser);
	Assert.assertTrue(highestRankUser.getRank().equals(120));
    }

}
