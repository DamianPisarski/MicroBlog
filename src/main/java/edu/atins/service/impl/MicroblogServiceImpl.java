package edu.atins.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.atins.dao.FollowerDao;
import edu.atins.dao.UzytkownikDao;
import edu.atins.dao.WiadomoscDao;
import edu.atins.model.Post;
import edu.atins.model.User;
import edu.atins.service.MicroblogService;

@Service
public class MicroblogServiceImpl implements MicroblogService {

    @Autowired
    private FollowerDao followerDao;

    @Autowired
    private UzytkownikDao uzytkownikDao;

    @Autowired
    private WiadomoscDao wiadomoscDao;

    @Override
    @Transactional
    public void registerUser(User user) {
        uzytkownikDao.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return uzytkownikDao.getUserByUsername(username);
    }

    @Override
    @Transactional
    public void followUser(User follower, User followed) {
        if (follower != null && followed != null && !follower.equals(followed)) {
            if (!followerDao.isFollowing(follower, followed)) {
                followerDao.follow(follower, followed);
            }
        }
    }

    @Override
    @Transactional
    public void unfollowUser(User follower, User followed) {
        followerDao.unfollow(follower, followed);
    }

    @Override
    public boolean isFollowing(User follower, User followed) {
        return followerDao.isFollowing(follower, followed);
    }

    @Override
    @Transactional
    public void createPost(Post post) {
        if (post.getAuthor() == null) {
            throw new NullPointerException("Post author cannot be null");
        }
        if (post.getContent() == null || post.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }
        wiadomoscDao.addPost(post);
    }

    @Override
    public List<Post> getUserTimeline(User user) {
        return wiadomoscDao.getFullTimeline(user);
    }

    @Override
    public List<Post> getAllPosts() {
        return wiadomoscDao.getAllPosts();
    }

    @Override
    public List<Post> getPostsByAuthor(User author) {
        return wiadomoscDao.getPostsByUser(author);
    }
}