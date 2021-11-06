package edu.mikhailov;

import java.util.Objects;

public class Movie {
    private Integer id;
    private final String title;
    private final String description;
    private final int year;

    public Movie(String title, String description, int year) {
        this(null, title, description, year);
    }

    public Movie(Integer id, String title, String description, int year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().isInstance(o.getClass())) return false;
        Movie movie = (Movie) o;
        return year == movie.year && id.equals(movie.id) && title.equals(movie.title) && description.equals(movie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, year);
    }

    @Override
    public String toString() {
        return "Movie{id="+id+", title="+title+", description="+description+", year="+year+"}";
    }
}
