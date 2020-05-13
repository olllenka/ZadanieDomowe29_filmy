package com.example.demo.movie.model;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MovieDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Movie movie){
        entityManager.persist(movie);
    }

    public Movie getMovie(long id){
        Movie movie = entityManager.find(Movie.class, id);
        return movie;
    }

    @Transactional
    public void update(Movie movie){
        entityManager.merge(movie);
    }

    @Transactional
    public void remove(long id){
        Movie movieToRemove = entityManager.find(Movie.class, id);
        entityManager.remove(movieToRemove);
    }

    public List<Movie> getAll() {
        final String getAll = "SELECT m FROM Movie m";
        TypedQuery<Movie> getAllQuery = entityManager.createQuery(getAll, Movie.class);
        List<Movie> resultList = getAllQuery.getResultList();
        return resultList;
    }

    public List<Movie> getByCategory(MovieCategory category) {
        final String getByCategory = "SELECT m FROM Movie m WHERE m.category = :category";
        TypedQuery<Movie> getByCategoryQuery = entityManager.createQuery(getByCategory, Movie.class);
        getByCategoryQuery.setParameter("category", category);
        List<Movie> resultList = getByCategoryQuery.getResultList();
        return resultList;
    }
}
