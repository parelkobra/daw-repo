package cat.marianao.daw2.m07.uf3.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;

@WebServlet("/create-user")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 6792452551660856151L;

    @Inject
    private UserService userService;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String name = request.getParameter("name");
	String email = request.getParameter("email");

	if (!usernameExists(userService, username) && !emailExists(userService, email)) {
	    User user = getNewUser(username, password, name, email);
	    userService.create(user);
	    request.setAttribute("userCreated", true);
	} else {
	    request.setAttribute("userCreated", false);
	}

	request.getRequestDispatcher("/").forward(request, response);
    }

    private static User getNewUser(String username, String password, String name, String email) {
	User user = new User();
	user.setUsername(username);
	user.setName(name);
	user.setEmail(email);
	user.setPassword(password);
	user.setRank(0);
	user.setActive(true);
	user.setCreatedOn(new Timestamp(new Date().getTime()));

	return user;
    }

    private static boolean usernameExists(UserService userService, String username) {
	return userService.findUserByUsername(username) != null ? true : false;
    }

    private static boolean emailExists(UserService userService, String email) {
	return userService.findUserByEmail(email) != null ? true : false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

}
