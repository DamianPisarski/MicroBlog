package edu.atins.impl;

import edu.atins.dao.FollowerDao;
import edu.atins.model.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FollowerDaoImplTest {

    private FollowerDao followerDao = new FollowerDao() {

        // najprostsza możliwa "baza"
        private final Set<String> follows = new HashSet<>();

        @Override
        public void follow(User follower, User followed) {
            follows.add(follower.getUsername() + "->" + followed.getUsername());
        }

        @Override
        public void unfollow(User follower, User followed) {
            follows.remove(follower.getUsername() + "->" + followed.getUsername());
        }

        @Override
        public boolean isFollowing(User follower, User followed) {
            return follows.contains(follower.getUsername() + "->" + followed.getUsername());
        }
    };

    @Test
    void testFollowAndIsFollowing() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        followerDao.follow(user1, user2);

        assertTrue(followerDao.isFollowing(user1, user2));
    }

    @Test
    void testUnfollow() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        followerDao.follow(user1, user2);
        assertTrue(followerDao.isFollowing(user1, user2));

        followerDao.unfollow(user1, user2);
        assertFalse(followerDao.isFollowing(user1, user2));
    }
}
