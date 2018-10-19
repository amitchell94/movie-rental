package logic;

import logic.movie.Movie;
import logic.movie.MovieRepository;
import logic.movie.MoviesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesServiceTest {

    public static final int INVALID_MOVIE_ID = -1;
    private Movie movie1;
    private Movie movie2;
    private MovieRepository movieRepositoryMock;
    private MoviesService movieService;

    @Before
    public void setUp() throws Exception {
        movie1 = new Movie();
        movie1.setId(1);
        movie2 = new Movie();
        movie2.setId(2);

        movieRepositoryMock = Mockito.mock(MovieRepository.class);
        movieService = new MoviesService(movieRepositoryMock);
    }

    @Test
    public void save() {
        movieService.save(movie1);

        verify(movieRepositoryMock).save(movie1);
    }

    @Test
    public void getAllMovies() {
        when(movieRepositoryMock.getAllMovies()).thenReturn(Arrays.asList(movie1,movie2));

        List<Movie> movieList = movieService.getAllMovies();

        assertThat(movieList).hasSize(2);
        assertThat(movieList).contains(movie1,movie2);
    }

    @Test
    public void getMovieFromTitle() {
        String title = "title";
        when(movieRepositoryMock.getMovieFromTitle(title)).thenReturn(movie1);

        Movie movie = movieService.getMovieFromTitle(title);

        assertThat(movie).isEqualTo(movie1);
    }

    @Test
    public void whenGettingMovieWithNullTitle_shouldReturnNull() {
        String title = null;
        when(movieRepositoryMock.getMovieFromTitle(title)).thenReturn(null);

        Movie movie = movieService.getMovieFromTitle(title);

        assertThat(movie).isEqualTo(null);
    }


    @Test
    public void whenGettingMovieWithValidID_shouldReturnMovie() {
        int id = 1;
        when(movieRepositoryMock.getMovieFromId(id)).thenReturn(movie1);

        Movie movie = movieService.getMovieFromId(id);

        assertThat(movie).isEqualTo(movie1);
    }

    @Test
    public void whenGettingMovieWithoutValidID_shouldReturnNull() {
        when(movieRepositoryMock.getMovieFromId(INVALID_MOVIE_ID)).thenReturn(null);

        Movie movie = movieService.getMovieFromId(INVALID_MOVIE_ID);

        assertThat(movie).isEqualTo(null);
    }
}