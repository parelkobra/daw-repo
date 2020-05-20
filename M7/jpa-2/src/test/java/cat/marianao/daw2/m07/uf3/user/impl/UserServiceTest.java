package cat.marianao.daw2.m07.uf3.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;
import cat.marianao.daw2.m07.uf3.service.impl.UserServiceImpl;
import cat.marianao.daw2.m07.uf3.user.mock.Mock;

@RunWith(Arquillian.class)
public class UserServiceTest {
    @Inject
    private UserService userService;

    @Deployment(testable = true)
    public static JavaArchive createTestableDeployment() {
	final JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "example.jar")
		.addClasses(UserService.class, UserServiceImpl.class)
		.addAsManifestResource("META-INF/persistence-test.xml", "persistence.xml")
		.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	return jar;
    }

    @Test
    public void findAllActiveUsers() {
	User user0 = Mock.getUser0();
	User user1 = Mock.getUser1();

	userService.create(user0);
	userService.create(user1);

	List<User> users = userService.getAllActiveUsers();
	Assert.assertEquals(2, users.size());
    }

    @Test
    public void deactiveUser() throws Exception {
	User user2 = Mock.getUser2();
	userService.create(user2);

	userService.deactivateUser(user2);

	User resultUser = userService.findUserByUsername(user2.getUsername());
	Assert.assertEquals(false, resultUser.getActive());
    }

    @Test
    public void removeUser() {
	User user3 = Mock.getUser3();
	userService.create(user3);

	userService.remove(user3);

	User resultUser = userService.findUserByUsername(user3.getUsername());
	Assert.assertNull(resultUser);
    }

    @Test
    public void findUserByUsername() {
	User user4 = Mock.getUser4();
	userService.create(user4);

	User resultUser = userService.findUserByUsername(user4.getUsername());
	Assert.assertNotNull(resultUser);
	Assert.assertEquals(resultUser.getUserId(), user4.getUserId());
    }

    @Test
    public void findUserByEmail() {
	User user5 = Mock.getUser5();
	userService.create(user5);

	User resultUser = userService.findUserByEmail(user5.getEmail());
	Assert.assertNotNull(resultUser);
	Assert.assertEquals(resultUser.getUserId(), user5.getUserId());
    }

    @Test
    public void createUser() {
	User user6 = Mock.getUser6();
	userService.create(user6);

	Assert.assertNotNull(user6);
    }

}
