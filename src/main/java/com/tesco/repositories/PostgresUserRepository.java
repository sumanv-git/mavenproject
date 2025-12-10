package com.tesco.repositories;

import com.tesco.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class PostgresUserRepository implements UserRepository {

    @PersistenceContext private EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findUserByName(String name) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void updateUser(String id, User updatedUser) {
        updatedUser.setId(id);
        em.merge(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        findById(id).ifPresent(user -> em.remove(user));
    }
}
