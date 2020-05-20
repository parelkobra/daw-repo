package cat.marianao.daw2.m07.uf4.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cat.marianao.daw2.m07.uf4.service.client.jaxws.TicketService;

@Controller
public class ShowController {
    private final TicketService showsWebService = new TicketService();

    @RequestMapping(value = "/shows", method = RequestMethod.GET)
    public ModelAndView shows() throws ServletException, IOException {
	ModelAndView modelview = new ModelAndView("shows");
	modelview.getModelMap().addAttribute("shows", showsWebService.getTicketServiceEndpointImplPort().getAllShows());
	return modelview;
    }

    @RequestMapping(value = "/makeReservation")
    public String makeReservation(@RequestParam("id") String id) throws ServletException, IOException {
	showsWebService.getTicketServiceEndpointImplPort().makeReservation(id);
	return "redirect:/shows";
    }

    @RequestMapping(value = "/cancelReservation")
    public String cancelReservation(@RequestParam("id") String id) throws ServletException, IOException {
	showsWebService.getTicketServiceEndpointImplPort().cancelReservation(id);
	return "redirect:/shows";
    }
}
