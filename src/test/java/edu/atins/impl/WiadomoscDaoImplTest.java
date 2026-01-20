package edu.atins.impl;

import edu.atins.dao.FollowerDao;
import edu.atins.model.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WiadomoscDaoImplTest {

    // prosty "mock" Dao dla testów
    private FollowerDao followerDao = new FollowerDao() {
        private final Set<String> follows = new HashSet<>();

        @Override
        public void follow(User follower, User followee) {
            follows.add(follower.getUsername() + "->" + followee.getUsername());
        }

        @Override
        public void unfollow(User follower, User followee) {
            follows.remove(follower.getUsername() + "->" + followee.getUsername());
        }

        @Override
        public boolean isFollowing(User follower, User followee) {
            return follows.contains(follower.getUsername() + "->" + followee.getUsername());
        }
    };

    @Test
    void testFollowAndIsFollowing() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        followerDao.follow(user1, user2);

        assertTrue(followerDao.isFollowing(user1, user2), "user1 powinien obserwować user2");
    }

    @Test
    void testUnfollow() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        followerDao.follow(user1, user2);
        assertTrue(followerDao.isFollowing(user1, user2), "user1 powinien obserwować user2");

        followerDao.unfollow(user1, user2);
        assertFalse(followerDao.isFollowing(user1, user2), "user1 nie powinien już obserwować user2");
    }
}
