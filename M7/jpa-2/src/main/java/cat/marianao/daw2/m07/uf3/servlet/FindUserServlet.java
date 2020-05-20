package cat.marianao.daw2.m07.uf3.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;

@WebServlet("/find-user")
public class FindUserServlet extends HttpServlet {
    private static final long serialVersionUID = 5194473891824569052L;

    @Inject
    private UserService userService;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String option = request.getParameter("radioOption");
	String input = request.getParameter("input");

	List<User> user = findUser(option, input);
	if (user != null) {
	    request.setAttribute("users", user);
	}
	request.getRequestDispatcher("users.jsp").forward(request, response);
    }

    private List<User> findUser(String option, String input) {
	List<User> user = new ArrayList<>();
	if ("username".equalsIgnoreCase(option)) {
	    user.add(userService.findUserByUsername(input));
	}
	if ("email".equalsIgnoreCase(option)) {
	    user.add(userService.findUserByEmail(input));
	}
	return user;
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
