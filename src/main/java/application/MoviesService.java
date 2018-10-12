package application;

import data.MovieRepository;

import java.util.List;

public class MoviesService {
    private MovieRepository movieRepository;

    public MoviesService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovies () {
        return movieRepository.getAllMovies();
    }

    public Movie getMovieFromTitle (String title) {
        return movieRepository.getMovieFromTitle(title);
    }

    public Movie getMovieFromID (int movieId) {
        return movieRepository.getMovieFromId(movieId);
    }
}
