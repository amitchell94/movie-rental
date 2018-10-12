package application;

import data.RentalRepository;

import java.time.LocalDate;
import java.util.List;

public class RentalsService {
    private RentalRepository rentalRepository;

    public RentalsService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals () {
        return rentalRepository.getAllRentals();
    }

    public static Rental createRental(int customerID, int movieID, LocalDate rentalDate, LocalDate returnDate, Double cost) {
        Rental rental = new Rental();
        rental.setCustomerID(customerID);
        rental.setMovieID(movieID);
        rental.setRentalDate(rentalDate);
        rental.setReturnDate(returnDate);
        rental.setCost(cost);
        return rental;
    }

    public Rental returnRental(int customerID, int movieID, LocalDate returnDate, Double cost) {
        return rentalRepository.returnRental(customerID,movieID,returnDate,cost);
    }

    public List<Integer> getRentedMovieIDs(int customerID) {
        return rentalRepository.getRentedMovieIDs(customerID);
    }

    public boolean movieIsRented (int customerId, int movieId) {
        return rentalRepository.movieIsRented(customerId,movieId);
    }
}
