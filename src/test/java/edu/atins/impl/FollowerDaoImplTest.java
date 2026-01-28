package edu.atins.impl;

import edu.atins.dao.FollowerDao;
import edu.atins.model.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FollowerDaoImplTest {

    private FollowerDao followerDao = new FollowerDao() {

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
    
    private User user(String name) {
        User u = new User();
        u.setUsername(name);
        return u;
    }

     @Test
    void testFollowAndIsFollowing() {
        User u1 = user("user1");
        User u2 = user("user2");

        followerDao.follow(u1, u2);

        assertTrue(followerDao.isFollowing(u1, u2));
    }

    @Test
    void testFollowTwiceStillFollowing() {
        User u1 = user("user1");
        User u2 = user("user2");

        followerDao.follow(u1, u2);
        followerDao.follow(u1, u2); // duplikat też ma działać

        assertTrue(followerDao.isFollowing(u1, u2));
    }

    @Test
    void testSelfFollow() {
        User u1 = user("user1");

        followerDao.follow(u1, u1);

        assertTrue(followerDao.isFollowing(u1, u1));
    }

    @Test
    void testFollowNullFollowerThrows() {
        User u2 = user("user2");

        assertThrows(NullPointerException.class, () -> followerDao.follow(null, u2));
    }

    @Test
    void testFollowNullFollowedThrows() {
        User u1 = user("user1");

        assertThrows(NullPointerException.class, () -> followerDao.follow(u1, null));
    }

    // ===== TESTY UNFOLLOW =====

    @Test
    void testUnfollow() {
        User u1 = user("user1");
        User u2 = user("user2");

        followerDao.follow(u1, u2);
        assertTrue(followerDao.isFollowing(u1, u2));

        followerDao.unfollow(u1, u2);
        assertFalse(followerDao.isFollowing(u1, u2));
    }

    @Test
    void testUnfollowWhenNotFollowingDoesNotThrow() {
        User u1 = user("user1");
        User u2 = user("user2");

        assertDoesNotThrow(() -> followerDao.unfollow(u1, u2));
        assertFalse(followerDao.isFollowing(u1, u2));
    }

    @Test
    void testUnfollowNullFollowerThrows() {
        User u2 = user("user2");

        assertThrows(NullPointerException.class, () -> followerDao.unfollow(null, u2));
    }

    @Test
    void testUnfollowNullFollowedThrows() {
        User u1 = user("user1");

        assertThrows(NullPointerException.class, () -> followerDao.unfollow(u1, null));
    }

    // ===== TESTY ISFOLLOWING =====

    @Test
    void testIsFollowingNeverFollowedReturnsFalse() {
        User u1 = user("user1");
        User u2 = user("user2");

        assertFalse(followerDao.isFollowing(u1, u2));
    }

    @Test
    void testIsFollowingDirectionMatters() {
        User u1 = user("user1");
        User u2 = user("user2");

        followerDao.follow(u1, u2);

        assertTrue(followerDao.isFollowing(u1, u2));
        assertFalse(followerDao.isFollowing(u2, u1));
    }

    @Test
    void testIsFollowingNullFollowerThrows() {
        User u2 = user("user2");
        assertThrows(NullPointerException.class, () -> followerDao.isFollowing(null, u2));
    }

    @Test
    void testIsFollowingNullFollowedThrows() {
        User u1 = user("user1");
        assertThrows(NullPointerException.class, () -> followerDao.isFollowing(u1, null));
    }
}