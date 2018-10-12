package presentation;

import application.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private MoviesService moviesService;
    private RentalsService rentalsService;
    private CustomersService customersService;

    public Menu(MoviesService moviesService, RentalsService rentalsService, CustomersService customersService) {
        this.moviesService = moviesService;
        this.rentalsService = rentalsService;
        this.customersService = customersService;
    }

    private Scanner scanner = new Scanner(System.in);

    public void startMenu() {

        boolean finish = false;

        while (!finish) {
            System.out.println("Welcome to Blockbuster, what would you like to do?");
            System.out.println("0 - View all available movies");
            System.out.println("1 - Rent a movie");
            System.out.println("2 - Return a movie");
            System.out.println("3 - Exit");

            int option = scanner.nextInt();

            switch (option) {
                case 0:
                    printMovieList();
                    break;
                case 1:
                    rentMovie();
                    break;
                case 2:
                    returnMovie();
                    break;
                case 3:
                    finish = true;
                    break;
                default:
                    System.out.println("Not a valid input");
            }
        }

        System.out.println("Thanks for visiting Blockbuster!");
    }

    private void rentMovie() {

        while (true) {
            System.out.println("Please enter your name or type 0 to go back to the main menu");
            String customerName = scanner.next();

            if("0".equals(customerName)){
                return;
            }

            Customer customer = customersService.getCustomerFromName(customerName);

            if (customer.getName() != null) {
                Movie movie = selectMovieToRent(customer.getId());
                if (movie != null) {
                    Rental rental = rentalsService.createRental(customer.getId(), movie.getId(), LocalDate.now(), null, null);
                    rentalsService.save(rental);
                    System.out.println("Okay " + customer.getName() + ", you have rented "
                            + movie.getTitle() + ". Thanks for your custom!");
                    System.out.println();
                    return;
                }
            } else {
                System.out.println("Name not recognised, please try again.");
            }
        }
    }

    private Movie selectMovieToRent(int customerID) {
        Movie movie = null;


        //TODO need to make it so the user can only rent movies they haven't rented before.
        printMovieList();
        boolean finished = false;
        while (!finished) {
            System.out.println("Please type in the movie title you wish to rent, type 0 to go back");
            String movieTitle = scanner.nextLine();
            movieTitle = scanner.nextLine();
            if (!"0".equals(movieTitle)) {
                movie = moviesService.getMovieFromTitle(movieTitle);
            } else {
                return null;
            }
            if (movie.getTitle() != null) {
                if (!rentalsService.movieIsRented(customerID,movie.getId())) {
                    return movie;
                } else {
                    System.out.println("You have already rented this movie!");
                }
            } else {
                System.out.println("Invalid movie title");
            }
        }
        return null;
    }

    private void returnMovie() {

        while (true) {
            System.out.println("Please enter your name or type 0 to go back to the main menu");
            String customerName = scanner.next();

            if("0".equals(customerName)){
                return;
            }

            Customer customer = customersService.getCustomerFromName(customerName);

            if (customer.getName() != null) {
                Movie movie = selectMovieToReturn(customer.getId());
                if (movie != null) {
                    Rental rental = rentalsService.returnRental(customer.getId(), movie.getId(), LocalDate.now(), movie.getPrice());
                    System.out.println("Okay " + customer.getName() + ", you have returned "
                            + movie.getTitle() + ". The total cost will be £" + rental.getCost() +". Thanks for your custom!");
                    System.out.println();
                    return;
                }
            } else {
                System.out.println("Name not recognised, please try again.");
            }
        }
    }

    private Movie selectMovieToReturn(int customerID) {
        Movie movie = null;
        List<Integer> movieIdList = rentalsService.getRentedMovieIDs(customerID);
        List<Movie> rentedMovies = new ArrayList<>();
        for (Integer movieId : movieIdList) {
            rentedMovies.add(moviesService.getMovieFromId(movieId));
        }

        if (rentedMovies.size() > 0) {
            System.out.println("You have rented the following movies:");
            for (Movie rentedMovie : rentedMovies) {
                System.out.println(rentedMovie.getTitle());
            }
        } else {
            System.out.println("You haven't rented any movies.");
            return null;
        }
        scanner.nextLine();
        boolean finished = false;
        while (!finished) {
            System.out.println("Please type in the movie title you wish to return, type 0 to go back");
            String movieTitle = scanner.nextLine();
            if (!"0".equals(movieTitle)) {
                movie = moviesService.getMovieFromTitle(movieTitle);
            } else {
                return null;
            }
            if (movie.getTitle() != null && rentalsService.movieIsRented(customerID,movie.getId())) {
                return movie;
            } else {
                System.out.println("Invalid movie title");
            }
        }
        return null;
    }

    private void printMovieList() {
        System.out.println("The current available movies are:");
        for (Movie movie : moviesService.getAllMovies()) {
            System.out.println(movie.getTitle() + " (" + movie.getYear() + ")");
            System.out.println("Starring: " + movie.getActor());
            System.out.println("Genre: " + movie.getGenre());
            System.out.println("Price: £" + movie.getPrice());
            System.out.println();
        }
    }
}

