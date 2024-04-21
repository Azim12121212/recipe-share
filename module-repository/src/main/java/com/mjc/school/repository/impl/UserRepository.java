package com.mjc.school.repository.impl;

import com.mjc.school.repository.UserRepositoryInterface;
import com.mjc.school.repository.model.UserModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserRepositoryInterface {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserModel> readAll(Integer pageNum, Integer pageSize, String sortBy) {
        String jpql = "SELECT u FROM UserModel u ORDER BY u." + sortBy;

        Query query = entityManager.createQuery(jpql);
        if (pageNum!=null && pageSize!=null) {
            query.setFirstResult((pageNum-1)*pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public Optional<UserModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(UserModel.class, id));
    }

    @Override
    public UserModel create(UserModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public UserModel update(UserModel entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(entityManager.find(UserModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(UserModel.class, id)!=null;
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        UserModel userModel = (UserModel) entityManager
                .createQuery("SELECT u FROM UserModel u WHERE u.email = :email")
                .setParameter("email", email).getResultList()
                .stream().findFirst().orElse(null);
        return Optional.ofNullable(userModel);
    }}