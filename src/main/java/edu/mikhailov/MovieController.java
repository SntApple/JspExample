package edu.mikhailov;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MovieController extends HttpServlet {
    private static final Logger LOGGER = getLogger(MovieController.class);
    public static final String DELETE = "delete";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String ALL = "all";
    public final MovieDao movieDao;

    public MovieController() {
        movieDao = new MovieDao(DataSourceFactory.getConnection());
    }

    public MovieController(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null)
            action = ALL;
        if (action.equals(DELETE)) {
            int id = getId(request);
            movieDao.delete(id);
            response.sendRedirect("movies");
        } else if (action.equals(CREATE) || action.equals(UPDATE)){
            final Movie movie = CREATE.equals(action) ? new Movie("", "", 1) : movieDao.get(getId(request));
            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/movieForm.jsp").forward(request, response);
        } else {
            request.setAttribute("movies", movieDao.findAll());
            request.getRequestDispatcher("/movies.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Movie movie = new Movie(id.isEmpty() ? null : Integer.valueOf(id), request.getParameter("title"),
                request.getParameter("description"), Integer.parseInt(request.getParameter("year")));

        LOGGER.info(movie.isNew() ? "Create {}" : "Update {}", movie);
        movieDao.save(movie);
        response.sendRedirect("movies");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return  Integer.parseInt(paramId);
    }
}
