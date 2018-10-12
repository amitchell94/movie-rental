package application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RentalsServiceTest {

    final static int VALID_RENTAL_ID = 1;
    final static int VALID_CUSTOMER_ID = 1;
    final static int VALID_MOVIE_ID = 1;
    final static LocalDate VALID_RENTAL_DATE = LocalDate.of(2018,1,1);
    final static LocalDate VALID_RETURN_DATE = LocalDate.of(2018,2,2);
    final static Double VALID_PRICE = 1.99;
    final static Double VALID_COST = 1.99;

    private Rental rental1;
    private Rental rental2;
    private RentalRepository rentalRepositoryMock;
    private RentalsService rentalsService;


    @Before
    public void setUp() throws Exception {
        rental1 = new Rental();
        rental2 = new Rental();

        rentalRepositoryMock = Mockito.mock(RentalRepository.class);
        rentalsService = new RentalsService(rentalRepositoryMock);
    }

    @Test
    public void returnRental() {
        rental1.setId(VALID_RENTAL_ID);
        rental1.setCustomerID(VALID_CUSTOMER_ID);
        rental1.setMovieID(VALID_MOVIE_ID);
        rental1.setRentalDate(VALID_RENTAL_DATE);
        rental1.setReturnDate(VALID_RETURN_DATE);;
        rental1.setCost(VALID_COST);

        long daysRented =  ChronoUnit.DAYS.between(rental1.getRentalDate(),VALID_RETURN_DATE);
        if (daysRented == 0) daysRented = 1;
        double totalCost = daysRented * VALID_PRICE;

        rental2.setId(VALID_RENTAL_ID);
        rental2.setCustomerID(VALID_CUSTOMER_ID);
        rental2.setMovieID(VALID_MOVIE_ID);
        rental2.setRentalDate(VALID_RENTAL_DATE);
        rental2.setReturnDate(VALID_RETURN_DATE);;
        rental2.setCost(totalCost);

        when(rentalRepositoryMock.getRentalFromCustAndMovIDs(VALID_CUSTOMER_ID,VALID_MOVIE_ID)).thenReturn(rental1);

        when(rentalRepositoryMock.getRentalFromRentalID(VALID_RENTAL_ID)).thenReturn(rental2);

        Rental rental = rentalsService.returnRental(VALID_CUSTOMER_ID,VALID_MOVIE_ID,VALID_RETURN_DATE,VALID_PRICE);

        verify(rentalRepositoryMock).returnRental(VALID_RETURN_DATE,totalCost,VALID_RENTAL_ID);

        assertThat(rental).isEqualTo(rental2);

    }
}