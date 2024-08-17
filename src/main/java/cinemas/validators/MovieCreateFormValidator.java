package cinemas.validators;

import cinemas.dtos.MovieDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MovieCreateFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MovieDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, org.springframework.validation.Errors errors) {
        MovieDto form = (MovieDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titleVn", "admin.movieDto.require", new Object[]{"tên phim (VI)", "movie title (VI)"}, "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titleEn", "admin.movieDto.require", new Object[]{"tên phim (EN)", "movie title (EN)"}, "");

        if (form.getGenreIds() == null || form.getGenreIds().isEmpty()) {
            errors.rejectValue("genres", "admin.movieDto.invalid.emptyList", new Object[]{"thể loại", "genre"}, "");
        }
    }
}
