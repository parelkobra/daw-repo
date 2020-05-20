package cat.marianao.daw2.m07.uf4.domain.repository;

import java.util.Collection;

import cat.marianao.daw2.m07.uf4.domain.Show;

public interface ShowRepository {
    Collection<Show> getAllShows();

    Show makeReservation(String showId);

    Show cancelReservation(String showId);
}
