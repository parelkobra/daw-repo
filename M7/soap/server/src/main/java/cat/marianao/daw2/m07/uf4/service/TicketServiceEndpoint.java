package cat.marianao.daw2.m07.uf4.service;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import cat.marianao.daw2.m07.uf4.domain.Show;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface TicketServiceEndpoint {
    @WebMethod
    ArrayList<Show> getAllShows();

    @WebMethod
    Show makeReservation(String showId);

    @WebMethod
    Show cancelReservation(String showId);
}
