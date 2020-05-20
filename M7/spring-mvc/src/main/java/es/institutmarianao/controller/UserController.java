package es.institutmarianao.controller;

import es.institutmarianao.domain.User;
import es.institutmarianao.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("customAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(Model model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.logout();
        return "redirect:/";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp()
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("user");
        User newUser = new User();
        modelview.getModelMap().addAttribute("newUser", newUser);
        return modelview;
    }
    
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String processSignUp(@ModelAttribute("newUser") User user, HttpServletRequest request)
            throws ServletException, IOException {
        if (userService.addUser(user)) {
            loginUser(user, request);
            return "redirect:/";
        }
        return "redirect:/signupfailed";
    }
    
    @RequestMapping(value = "/signupfailed", method = RequestMethod.GET)
    public ModelAndView signUpError(@ModelAttribute ("newUser") User user) {
        ModelAndView modelview = new ModelAndView("user");
        modelview.getModelMap().addAttribute(user);
        modelview.getModelMap().addAttribute("error", "true");
        return modelview;
    }

    @RequestMapping(value = "/secured/orders", method = RequestMethod.GET)
    public ModelAndView orders()
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("orders");
        return modelview;
    }

    @RequestMapping(value = "/secured/updateUser", method = RequestMethod.GET)
    public ModelAndView updateUser()
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelview = new ModelAndView("user");
        User user = userService.getUserByUsername(auth.getName());
        modelview.getModelMap().addAttribute("newUser", user);
        return modelview;
    }
    
    @RequestMapping(value = "/secured/updateUser", method = RequestMethod.POST)
    public String processUpdateUser(@ModelAttribute("newUser") User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!user.getUsername().equals(auth.getPrincipal())) {
            throw new RuntimeException("Attempting to change user name");
        }
        userService.updateUser(user);
        return "redirect:/";
    }

    private void loginUser(User user, HttpServletRequest request) {
        try {
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authentication = authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
