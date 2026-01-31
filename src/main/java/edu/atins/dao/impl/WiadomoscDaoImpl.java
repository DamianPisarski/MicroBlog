package edu.atins.dao.impl;

import edu.atins.dao.WiadomoscDao;
import edu.atins.model.Post;
import edu.atins.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class WiadomoscDaoImpl implements WiadomoscDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Post> getPostsByUser(User user) {
        return entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.author = :user ORDER BY p.createdAt DESC", Post.class)
                .setParameter("user", user)
                .getResultList();
    }
    
    @Override
    public List<Post> getPostsByUserId(Long id) {
        return entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.author.id = :id ORDER BY p.createdAt DESC", Post.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Post> getFullTimeline(User user) {
        return entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.author = :user OR p.author IN " +
                        "(SELECT f.followed FROM Follower f WHERE f.follower = :user) " +
                        "ORDER BY p.createdAt DESC", Post.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Post> getAllPosts() {
        return entityManager.createQuery(
                "SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class)
                .getResultList();
    }

    @Override
    public void addPost(Post post) {
        entityManager.persist(post);
    }
}
