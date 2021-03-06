package logic.movie;

import java.util.List;

public interface MovieRepository {

    void save(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovieFromTitle(String title);
    Movie getMovieFromId(int movieId);
}
