package application;

import application.Movie;
import application.Rental;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository {
    void save(Rental rental);
    List<Rental> getAllRentals();

    List<Integer> getRentedMovieIDs(int customerID);

    boolean movieIsRented (int customerId, int movieId);

    void returnRental(LocalDate returnDate, Double totalCost, int rentalID);

    Rental getRentalFromCustAndMovIDs (int customerID, int movieID);

    Rental getRentalFromRentalID (int rentalID);
}
