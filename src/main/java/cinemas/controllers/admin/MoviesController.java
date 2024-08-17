package cinemas.controllers.admin;

import cinemas.enums.MovieStatus;
import cinemas.models.Movie;
import cinemas.services.GenresService;
import cinemas.services.MoviesService;
import cinemas.dtos.MovieDto;
import cinemas.validators.MovieCreateFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@RequestMapping("/admin/movies")
@Controller("adminMoviesController")
public class MoviesController {
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);

    @Autowired
    private MoviesService moviesService;
    @Autowired
    private GenresService genresService;
    @Autowired
    private MovieCreateFormValidator movieCreateFormValidator;
    @GetMapping

    public String index(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "status", defaultValue = "all") String status,
                        Model model) {
        var moviesPagination = moviesService.getPaginationMoviesByTitleAndStatus(keyword, page, size, status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("status", status);
        model.addAttribute("moviesPagination", moviesPagination);
        return "admin/movies/index";
    }

    @InitBinder("movieDto")
    public void movieDtoInitBinder(WebDataBinder binder) {
        binder.addValidators(movieCreateFormValidator);
    }

    @GetMapping(value = {"new", "new/"})
    public String getInsertForm(Model model) {
        if (!model.containsAttribute("movieDto")) {
            model.addAttribute("movieDto", new MovieDto());
        }
        model.addAttribute("allGenres", genresService.getAllGenres());
        model.addAttribute("allStatuses", MovieStatus.values());
        return "admin/movies/new";
    }

    @PostMapping(value = {"new", "new/"})
    public String insertMovie(@Validated @ModelAttribute("movieDto") MovieDto movieDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movieDto", bindingResult);
            redirectAttributes.addFlashAttribute("movieDto", movieDto);
            return "redirect:/admin/movies/new";
        }

        try {
            moviesService.save(movieDto);
            redirectAttributes.addFlashAttribute("successMessage", "admin.movie.update.success");
        } catch (Exception e) {
            logger.error("Error while updating movie: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "admin.movie.update.error");
        }

        return "redirect:/admin/movies/new";
    }

    @GetMapping(value = {"update/{id}", "update/{id}/"})
    public String getUpdateForm(@PathVariable("id") int id, Model model) {
        Movie movie = moviesService.findById(id).orElse(null);
        if (movie == null) {
            return "redirect:/admin/movies";
        }

        if (!model.containsAttribute("movieDto")) {
            model.addAttribute("movieDto", new MovieDto(movie));
        }
        model.addAttribute("allGenres", genresService.getAllGenres());
        model.addAttribute("allStatuses", MovieStatus.values());
        return "admin/movies/update";
    }

    @PostMapping(value = {"update", "update/"})
    public String updateMovie(@Validated @ModelAttribute("movieDto") MovieDto movieDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation error: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movieDto", bindingResult);
            redirectAttributes.addFlashAttribute("movieDto", movieDto);
            return "redirect:/admin/movies/update/" + movieDto.getId();
        }

        try {
            moviesService.update(movieDto);
            redirectAttributes.addFlashAttribute("successMessage", "admin.movie.update.success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while updating movie: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "admin.movie.update.error");
        }

        return "redirect:/admin/movies/update/" + movieDto.getId();
    }
}
