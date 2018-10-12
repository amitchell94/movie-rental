package application;

import java.util.List;

public class MoviesService {
    private MovieRepository movieRepository;

    public MoviesService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public Movie getMovieFromTitle(String title) {
        if (title.equals("")) {
            return null;
        }
        return movieRepository.getMovieFromTitle(title);
    }

    public Movie getMovieFromId(int movieId) {
        return movieRepository.getMovieFromId(movieId);
    }
}
