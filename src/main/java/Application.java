import application.CustomersService;
import application.MoviesService;
import application.Rental;
import application.RentalsService;
import data.*;
import presentation.Menu;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        RentalRepository dbRentalRepository = new DbRentalRepository();
        MovieRepository dbMovieRepository = new DbMovieRepository();
        CustomerRepository dbCustomerRepository = new DbCustomerRepository();

        RentalsService rentalsService = new RentalsService(dbRentalRepository);
        MoviesService moviesService = new MoviesService(dbMovieRepository);
        CustomersService customersService = new CustomersService(dbCustomerRepository);

        Menu menu = new Menu();

        menu.startMenu(moviesService, rentalsService, customersService);


        List<Rental> rentalList = rentalsService.getAllRentals();
        System.out.println(rentalList.toString());

        //Rental rental1 = createRental(1,5,"1994-08-09","1994-06-06",6.99);

        //rentalsService.save(rental1);
    }
}
