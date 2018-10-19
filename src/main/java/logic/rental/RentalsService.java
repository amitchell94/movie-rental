package logic.rental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalsService {
    private RentalRepository rentalRepository;

    public RentalsService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
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

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.getAllRentals();
    }

    public Rental returnRental(int customerID, int movieID, LocalDate returnDate, Double price) {
        Rental rental = rentalRepository.getRentalFromCustomerAndMovieIDs(customerID, movieID);

        rentalRepository.returnRental(returnDate, getTotalCost(returnDate, price, rental), rental.getId());

        return rentalRepository.getRentalFromRentalID(rental.getId());
    }

    private double getTotalCost(LocalDate returnDate, Double price, Rental rental) {
        long daysRented = ChronoUnit.DAYS.between(rental.getRentalDate(), returnDate);
        if (daysRented == 0) daysRented = 1;
        return daysRented * price;
    }

    public List<Integer> getRentedMovieIDs(int customerId) {
        return rentalRepository.getRentedMovieIDs(customerId);
    }

    public boolean movieIsRented(int customerId, int movieId) {
        return rentalRepository.movieIsRented(customerId, movieId);
    }
}
