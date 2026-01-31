package edu.atins.dao.impl;

import edu.atins.dao.UzytkownikDao;
import edu.atins.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UzytkownikDaoImpl implements UzytkownikDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }
}
