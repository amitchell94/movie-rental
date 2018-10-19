import logic.customer.CustomerRepository;
import logic.customer.CustomersService;
import logic.movie.MovieRepository;
import logic.movie.MoviesService;
import logic.rental.RentalRepository;
import logic.rental.RentalsService;
import data.*;
import presentation.Menu;

public class Application {

    public static void main(String[] args) {
        RentalRepository dbRentalRepository = new DbRentalRepository();
        MovieRepository dbMovieRepository = new DbMovieRepository();
        CustomerRepository dbCustomerRepository = new DbCustomerRepository();

        RentalsService rentalsService = new RentalsService(dbRentalRepository);
        MoviesService moviesService = new MoviesService(dbMovieRepository);
        CustomersService customersService = new CustomersService(dbCustomerRepository);

        Menu menu = new Menu(moviesService, rentalsService, customersService);

        menu.startMenu();

    }
}
