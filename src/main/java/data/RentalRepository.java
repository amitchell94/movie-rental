package data;

import application.Movie;
import application.Rental;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository {
    void save(Rental rental);
    List<Rental> getAllRentals();

    List<Integer> getRentedMovieIDs(int customerID);

    boolean movieIsRented (int customerId, int movieId);

    Rental returnRental(int customerID, int movieID, LocalDate returnDate, Double price);
}
