package edu.atins.dao.impl;

import edu.atins.dao.FollowerDao;
import edu.atins.model.Follower;
import edu.atins.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;;

@Repository
@Transactional
public class FollowerDaoImpl implements FollowerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void follow(User follower, User followed) {
        Follower f = new Follower();
        f.setFollower(follower);
        f.setFollowed(followed);
        entityManager.persist(f);
    }

    @Override
    public void unfollow(User follower, User followed) {
        Follower f = entityManager.createQuery(
                "SELECT f FROM Follower f WHERE f.follower = :follower AND f.followed = :followed", Follower.class)
                .setParameter("follower", follower)
                .setParameter("followed", followed)
                .getSingleResult();
        entityManager.remove(f);
    }

    @Override
    public boolean isFollowing(User follower, User followed) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(f) FROM Follower f WHERE f.follower = :follower AND f.followed = :followed", Long.class)
                .setParameter("follower", follower)
                .setParameter("followed", followed)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Follower> getFollowers(Long Id) {
        return entityManager.createQuery(
            "SELECT f FROM Follower f WHERE f.followed.id = :id", Follower.class)
            .setParameter("id", Id)
            .getResultList();
    }

    @Override
    public List<Follower> getFollowed(Long Id) {
        return entityManager.createQuery(
            "SELECT f FROM Follower f WHERE f.follower.id = :id", Follower.class)
            .setParameter("id", Id)
            .getResultList();
    }
}
