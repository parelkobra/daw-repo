package cat.marianao.daw2.m07.uf4.service.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import cat.marianao.daw2.m07.uf4.domain.Show;
import cat.marianao.daw2.m07.uf4.domain.repository.ShowRepository;
import cat.marianao.daw2.m07.uf4.domain.repository.impl.InMemoryShowRepository;
import cat.marianao.daw2.m07.uf4.service.TicketServiceEndpoint;

@WebService(serviceName = "TicketService", endpointInterface = "cat.marianao.daw2.m07.uf4.service.TicketServiceEndpoint")
public class TicketServiceEndpointImpl implements TicketServiceEndpoint {
    private final ShowRepository showRepository = new InMemoryShowRepository();

    @Override
    public ArrayList<Show> getAllShows() {
	return new ArrayList<>(showRepository.getAllShows());
    }

    @Override
    public Show makeReservation(String showId) {
	return showRepository.makeReservation(showId);
    }

    @Override
    public Show cancelReservation(String showId) {
	return showRepository.cancelReservation(showId);
    }

}
