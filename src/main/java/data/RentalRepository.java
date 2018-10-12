package data;

import application.Movie;
import application.Rental;

import java.util.List;

public interface RentalRepository {
    void save(Rental rental);
    List<Rental> getAllRentals();

    List<Integer> getRentedMovieIDs(int customerID);
}
