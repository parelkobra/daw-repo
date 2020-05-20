package cat.marianao.daw2.m07.uf4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("welcome");
        modelview.getModelMap().addAttribute("benvinguda", "Benvingut Reserva de tickets per concerts!");
        modelview.getModelMap().addAttribute("tagline", "Una aplicació de l'Institut Obert de Catalunya");
        return modelview;
    }

}
