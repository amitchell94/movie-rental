package logic;

import logic.rental.Rental;
import logic.rental.RentalRepository;
import logic.rental.RentalsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RentalsServiceTest {

    final static int VALID_RENTAL_ID = 1;
    final static int VALID_CUSTOMER_ID = 1;
    final static int VALID_MOVIE_ID = 1;
    final static LocalDate VALID_RENTAL_DATE = LocalDate.of(2018, 1, 1);
    final static LocalDate VALID_RETURN_DATE = LocalDate.of(2018, 2, 2);
    final static Double VALID_PRICE = 1.99;
    final static Double VALID_COST = 1.99;
    public static final int VALID_TOTAL_COST = 12;

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
        Rental rental1 = createRental();
        Rental rental2 = createRental();
        double totalCost = VALID_TOTAL_COST;
        rental2.setCost(totalCost);

        when(rentalRepositoryMock.getRentalFromCustomerAndMovieIDs(VALID_CUSTOMER_ID, VALID_MOVIE_ID)).thenReturn(rental1);
        when(rentalRepositoryMock.getRentalFromRentalID(VALID_RENTAL_ID)).thenReturn(rental2);

        Rental rental = rentalsService.returnRental(VALID_CUSTOMER_ID, VALID_MOVIE_ID, VALID_RETURN_DATE, VALID_PRICE);

        verify(rentalRepositoryMock).returnRental(VALID_RETURN_DATE, totalCost, VALID_RENTAL_ID);
        assertThat(rental).isEqualTo(rental2);

    }

    private Rental createRental() {
        Rental rental1 = new Rental();
        rental1.setId(VALID_RENTAL_ID);
        rental1.setCustomerID(VALID_CUSTOMER_ID);
        rental1.setMovieID(VALID_MOVIE_ID);
        rental1.setRentalDate(VALID_RENTAL_DATE);
        rental1.setReturnDate(VALID_RETURN_DATE);
        ;
        rental1.setCost(VALID_COST);
        return rental1;
    }
}