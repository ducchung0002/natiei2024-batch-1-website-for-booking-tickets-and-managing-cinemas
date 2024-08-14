package cinemas.services;

import cinemas.models.Movie;
import cinemas.validators.MovieValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MoviesService {
    List<Movie> getAllMovies();
    Movie save(Movie movie);
    Movie save(MovieValidator movieValidator);
    Movie uploadPhoto(Movie movie, MultipartFile photo);
}
