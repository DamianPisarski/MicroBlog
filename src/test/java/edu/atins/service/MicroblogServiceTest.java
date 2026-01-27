package edu.atins.service;

import edu.atins.model.Post;
import edu.atins.model.User;
import edu.atins.service.impl.MicroblogServiceImpl;
import edu.atins.dao.FollowerDao;
import edu.atins.dao.UzytkownikDao;
import edu.atins.dao.WiadomoscDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MicroblogServiceTest {

    private MicroblogServiceImpl microblogService;

    private final Set<String> follows = new HashSet<>();
    private final List<Post> postsDb = new ArrayList<>();
    private final List<User> usersDb = new ArrayList<>();

    @BeforeEach
    void setUp() {
        microblogService = new MicroblogServiceImpl();

        FollowerDao followerDao = new FollowerDao() {
            @Override
            public void follow(User f, User d) { follows.add(f.getUsername() + "->" + d.getUsername()); }
            @Override
            public void unfollow(User f, User d) { follows.remove(f.getUsername() + "->" + d.getUsername()); }
            @Override
            public boolean isFollowing(User f, User d) { return follows.contains(f.getUsername() + "->" + d.getUsername()); }
        };

        WiadomoscDao wiadomoscDao = new WiadomoscDao() {
            @Override
            public void addPost(Post p) { postsDb.add(p); }
            @Override
            public List<Post> getFullTimeline(User u) {
                return postsDb.stream()
                    .filter(p -> p.getAuthor().equals(u) || follows.contains(u.getUsername() + "->" + p.getAuthor().getUsername()))
                    .collect(Collectors.toList());
            }
            @Override public List<Post> getPostsByUser(User u) { return new ArrayList<>(); }
            @Override public List<Post> getAllPosts() { return postsDb; }
        };

        ReflectionTestUtils.setField(microblogService, "followerDao", followerDao);
        ReflectionTestUtils.setField(microblogService, "wiadomoscDao", wiadomoscDao);
        
        follows.clear();
        postsDb.clear();
    }

    private User user(String name) {
        User u = new User();
        u.setUsername(name);
        return u;
    }

    @Test
    void testFollowUserLogic() {
        User u1 = user("Adam");
        User u2 = user("Piotr");

        microblogService.followUser(u1, u2);
        assertTrue(microblogService.isFollowing(u1, u2), "Service should successfully process follow action");
    }

    @Test
    void testTimelineContainsFollowedUserPosts() {
        User Adam = user("Adam");
        User Piotr = user("Piotr");
        
        microblogService.followUser(Adam, Piotr);
        
        Post PiotrsPost = new Post();
        PiotrsPost.setAuthor(Piotr);
        PiotrsPost.setContent("Hello World");
        microblogService.createPost(PiotrsPost);

        List<Post> timeline = microblogService.getUserTimeline(Adam);
        assertEquals(1, timeline.size());
        assertEquals("Hello World", timeline.get(0).getContent());
    }

    @Test
    void testTimelineDoesNotShowUnfollowedUserPosts() {
        User Adam = user("Adam");
        User stranger = user("Stranger");

        Post strangersPost = new Post();
        strangersPost.setAuthor(stranger);
        strangersPost.setContent("Invisible post");
        microblogService.createPost(strangersPost);

        List<Post> timeline = microblogService.getUserTimeline(Adam);
        assertTrue(timeline.isEmpty(), "Adam should not see posts from people she doesn't follow");
    }

    @Test
    void testCannotFollowSelf() {
        User u1 = user("Ola");
        microblogService.followUser(u1, u1);
        assertFalse(microblogService.isFollowing(u1, u1), "Business logic should prevent following yourself");
    }

    @Test
    void testCreatePostWithNullAuthorThrows() {
        Post p = new Post();
        p.setContent("Content");
        p.setAuthor(null);
        assertThrows(NullPointerException.class, () -> microblogService.createPost(p));
    }

    @Test
    void testUnfollowWhenNotFollowingDoesNotError() {
        User u1 = user("Adam");
        User u2 = user("Piotr");
        assertDoesNotThrow(() -> microblogService.unfollowUser(u1, u2));
    }
}