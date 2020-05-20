package cat.marianao.daw2.m07.uf3.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cat.marianao.daw2.m07.uf3.config.ServicesTestConfig;
import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.repository.UserDAO;
import cat.marianao.daw2.m07.uf3.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesTestConfig.class })
public class UserServiceTest {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getUserOk() {
	/* Setup */
	String username = "test";
	User user = new User();
	user.setUsername(username);
	user.setUserId(1);

	when(userDAO.findUserByUsername(username)).thenReturn(user);

	/* Test */
	User userFromDb = userService.getUser(username);

	/* Verification */
	assertEquals(username, userFromDb.getUsername());
	assertEquals(new Integer(1), userFromDb.getUserId());
	verify(userDAO, times(1)).findUserByUsername(username);
    }
}