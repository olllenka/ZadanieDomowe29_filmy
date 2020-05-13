package com.example.demo.movie.controller;

import com.example.demo.movie.model.Movie;
import com.example.demo.movie.model.MovieCategory;
import com.example.demo.movie.model.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class MovieController {

    private MovieDao movieDao;

    @Autowired
    public MovieController(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("movie", new Movie());
        model.addAttribute("mode", "add");
        return "home";
    }

    @PostMapping("/")
    public String add(Movie movie){
        movieDao.save(movie);
        return "redirect:moviesList";
    }

    @GetMapping("/edit")
    public String editForm(Model model, @RequestParam Long id){
        Movie movie = movieDao.getMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("mode", "edit");
        return "home";
    }

    @PostMapping("/edit")
    public String edit(Movie movie){
        movieDao.update(movie);
        return "redirect:moviesList";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam Long id){
        movieDao.remove(id);
        return "redirect:moviesList";
    }

    @GetMapping("/moviesList")
    public String showMovies(Model model, @RequestParam(required = false) MovieCategory category){
        List<Movie> movies;
        List<MovieCategory> movieCategories = Arrays.asList(MovieCategory.values());
        if(category != null){
            movies = movieDao.getByCategory(category);
        } else {
            movies = movieDao.getAll();
        }
        model.addAttribute("movies", movies);
        model.addAttribute("movieCategories", movieCategories);
        return "moviesList";
    }

    @GetMapping("/movie")
    public String showMovie(Model model, @RequestParam Long id){
        Movie movie = movieDao.getMovie(id);
        model.addAttribute("movie", movie);
        return "movie";
    }
}
