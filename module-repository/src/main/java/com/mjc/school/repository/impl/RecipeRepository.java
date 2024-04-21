package com.mjc.school.repository.impl;

import com.mjc.school.repository.RecipeRepositoryInterface;
import com.mjc.school.repository.model.RecipeModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class RecipeRepository implements RecipeRepositoryInterface {
    private final DateTimeFormatter MY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RecipeModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        String jpql = "SELECT r FROM RecipeModel r ORDER BY r." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public Optional<RecipeModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(RecipeModel.class, id));
    }

    @Override
    public RecipeModel create(RecipeModel entity) {
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(MY_FORMAT));
        entity.setCreateDate(dateTime);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public RecipeModel update(RecipeModel entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(RecipeModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(RecipeModel.class, id)!=null;
    }
}