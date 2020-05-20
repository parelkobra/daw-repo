package cat.marianao.daw2.m07.uf4.service.client;

import java.util.List;

import cat.marianao.daw2.m07.uf4.service.client.jaxws.Show;
import cat.marianao.daw2.m07.uf4.service.client.jaxws.TicketService;
import cat.marianao.daw2.m07.uf4.service.client.jaxws.TicketServiceEndpoint;

public class TicketServiceClient {

    public static void main(String[] args) {
	TicketService service = new TicketService();
	TicketServiceEndpoint port = service.getTicketServiceEndpointImplPort();
	List<Show> list = port.getAllShows();
	printAllShows(list);
    }

    private static void printAllShows(List<Show> shows) {
	shows.stream().forEach(show -> {
	    System.out.println("Show [id=" + show.getId() + ", name=" + show.getName() + ", available tickets="
		    + show.getAvailableTickets() + "]");
	});
    }

}
