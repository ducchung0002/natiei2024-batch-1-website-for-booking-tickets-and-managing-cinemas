package cinemas.dtos;

import cinemas.enums.MovieStatus;
import cinemas.models.Genre;
import cinemas.models.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDto {
    private Integer id;
    private String titleVn;
    private String titleEn;
    private String languageVn;
    private String languageEn;
    private String descriptionVn;
    private String descriptionEn;
    private String releaseDate;
    private int runningTime; // minutes
    private int ageLimit;
    private String director;
    private String cast;
    private MovieStatus status;
    private String trailer;
    private MultipartFile photo;
    private String photoUrl;
    private List<Integer> genreIds;

    public MovieDto() {
    }

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.titleVn = movie.getTitleVn();
        this.titleEn = movie.getTitleEn();
        this.languageVn = movie.getLanguageVn();
        this.languageEn = movie.getLanguageEn();
        this.descriptionVn = movie.getDescriptionVn();
        this.descriptionEn = movie.getDescriptionEn();
        this.releaseDate = movie.getReleaseDate() != null
                ? movie.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Ho_Chi_Minh")))
                : null;
        this.runningTime = movie.getRunningTime();
        this.ageLimit = movie.getAgeLimit();
        this.director = movie.getDirector();
        this.cast = movie.getCast();
        this.status = movie.getStatus();
        this.trailer = movie.getTrailer();
        this.photoUrl = movie.getPhotoUrl();
        this.genreIds = movie.getGenres().stream().map(Genre::getId).toList();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getTitleVn() {
        return titleVn;
    }

    public void setTitleVn(String titleVn) {
        this.titleVn = titleVn;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getLanguageVn() {
        return languageVn;
    }

    public void setLanguageVn(String languageVn) {
        this.languageVn = languageVn;
    }

    public String getLanguageEn() {
        return languageEn;
    }

    public void setLanguageEn(String languageEn) {
        this.languageEn = languageEn;
    }

    public String getDescriptionVn() {
        return descriptionVn;
    }

    public void setDescriptionVn(String descriptionVn) {
        this.descriptionVn = descriptionVn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Movie toMovie() {
        Movie movie = new Movie();
        movie.setId(id);
        update(movie);
        return movie;
    }

    public void update(Movie movie) {
        movie.setTitleVn(titleVn);
        movie.setTitleEn(titleEn);
        movie.setLanguageVn(languageVn);
        movie.setLanguageEn(languageEn);
        movie.setDescriptionVn(descriptionVn);
        movie.setDescriptionEn(descriptionEn);
        movie.setReleaseDate(getReleaseDate() != null
                ? ZonedDateTime.parse(getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Ho_Chi_Minh")))
                : null);
        movie.setRunningTime(runningTime);
        movie.setAgeLimit(ageLimit);
        movie.setDirector(director);
        movie.setCast(cast);
        movie.setStatus(status);
        movie.setTrailer(trailer);
        movie.setGenres(new HashSet<>());
        this.getGenreIds().stream().map(genreId -> {
            var genre = new Genre();
            genre.setId(genreId);
            return genre;
        }).forEach(movie.getGenres()::add);
        movie.setPhotoUrl(photoUrl);
    }
}
