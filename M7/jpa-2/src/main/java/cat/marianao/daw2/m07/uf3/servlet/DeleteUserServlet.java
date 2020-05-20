package cat.marianao.daw2.m07.uf3.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cat.marianao.daw2.m07.uf3.domain.User;
import cat.marianao.daw2.m07.uf3.service.UserService;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String username = request.getParameter("username");
	User user = userService.findUserByUsername(username);
	if (user != null) {
	    userService.deactivateUser(user);
	}
	request.getRequestDispatcher("/").forward(request, response);
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
