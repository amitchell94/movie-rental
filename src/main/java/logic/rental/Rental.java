package logic.rental;

import java.time.LocalDate;

public class Rental {
    private Integer id;
    private int customerID;
    private int movieID;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private Double cost;

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", customerID=" + customerID +
                ", movieID=" + movieID +
                ", rentalDate='" + rentalDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", cost=" + cost +
                "}\n";
    }
}
