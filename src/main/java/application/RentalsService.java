package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public Rental returnRental(int customerID, int movieID, LocalDate returnDate, Double price) {

        Rental rental;

        rental = rentalRepository.getRentalFromCustAndMovIDs(customerID,movieID);

        long daysRented =  ChronoUnit.DAYS.between(rental.getRentalDate(),returnDate);
        if (daysRented == 0) daysRented = 1;
        double totalCost = daysRented * price;

        rentalRepository.returnRental(returnDate,totalCost,rental.getId());

        rental = rentalRepository.getRentalFromRentalID(rental.getId());

        return rental;
    }

    public List<Integer> getRentedMovieIDs(int customerID) {
        return rentalRepository.getRentedMovieIDs(customerID);
    }

    public boolean movieIsRented (int customerId, int movieId) {
        return rentalRepository.movieIsRented(customerId,movieId);
    }
}
