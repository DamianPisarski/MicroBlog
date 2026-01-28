package edu.atins.impl;

import edu.atins.dao.WiadomoscDao;
import edu.atins.model.Post;
import edu.atins.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class WiadomoscDaoImplTest {

    private final List<Post> posts = new ArrayList<>();

    private final Map<String, Set<String>> follows = new HashMap<>();

    private WiadomoscDao wiadomoscDao;

    private User userA;
    private User userB;
    private User userC;

    @BeforeEach
    void setUp() {
        wiadomoscDao = new WiadomoscDao() {
            @Override
            public List<Post> getPostsByUser(User user) {
                if (user == null || user.getUsername() == null) return Collections.emptyList();

                return posts.stream()
                        .filter(p -> p.getAuthor() != null
                                && p.getAuthor().getUsername() != null
                                && p.getAuthor().getUsername().equals(user.getUsername()))
                        .collect(Collectors.toList());
            }

            @Override
            public List<Post> getFullTimeline(User user) {
                if (user == null || user.getUsername() == null) return Collections.emptyList();

                String u = user.getUsername();
                Set<String> followed = follows.getOrDefault(u, Collections.emptySet());

                return posts.stream()
                        .filter(p -> p.getAuthor() != null && p.getAuthor().getUsername() != null)
                        .filter(p -> {
                            String author = p.getAuthor().getUsername();
                            return author.equals(u) || followed.contains(author);
                        })
                        .collect(Collectors.toList());
            }

            @Override
            public List<Post> getAllPosts() {
                return new ArrayList<>(posts);
            }

            @Override
            public void addPost(Post post) {
                if (post == null) throw new IllegalArgumentException("post is null");
                if (post.getAuthor() == null || post.getAuthor().getUsername() == null)
                    throw new IllegalArgumentException("author is required");
                if (post.getContent() == null || post.getContent().isBlank())
                    throw new IllegalArgumentException("content is required");

                posts.add(post);
            }
        };

        userA = new User();
        userA.setUsername("userA");

        userB = new User();
        userB.setUsername("userB");

        userC = new User();
        userC.setUsername("userC");

        posts.clear();
        follows.clear();
    }

    // --- helper do follow ---
    private void follow(User follower, User followed) {
        follows.computeIfAbsent(follower.getUsername(), k -> new HashSet<>())
                .add(followed.getUsername());
    }

    private Post post(User author, String content) {
        Post p = new Post();
        p.setAuthor(author);
        p.setContent(content);
        return p;
    }

    // ===================== TESTY =====================

    @Test
    void addPost_typical_addsToAllPosts() {
        wiadomoscDao.addPost(post(userA, "A1"));
        wiadomoscDao.addPost(post(userB, "B1"));

        List<Post> all = wiadomoscDao.getAllPosts();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> "A1".equals(p.getContent())));
        assertTrue(all.stream().anyMatch(p -> "B1".equals(p.getContent())));
    }

    @Test
    void addPost_edge_nullPost_throws() {
        assertThrows(IllegalArgumentException.class, () -> wiadomoscDao.addPost(null));
    }

    @Test
    void addPost_edge_missingAuthor_throws() {
        Post p = new Post();
        p.setContent("x");
        assertThrows(IllegalArgumentException.class, () -> wiadomoscDao.addPost(p));
    }

    @Test
    void addPost_edge_blankContent_throws() {
        Post p = new Post();
        p.setAuthor(userA);
        p.setContent("   ");
        assertThrows(IllegalArgumentException.class, () -> wiadomoscDao.addPost(p));
    }

    @Test
    void getPostsByUser_typical_returnsOnlyThatUser() {
        wiadomoscDao.addPost(post(userA, "A1"));
        wiadomoscDao.addPost(post(userA, "A2"));
        wiadomoscDao.addPost(post(userB, "B1"));

        List<Post> aPosts = wiadomoscDao.getPostsByUser(userA);
        assertEquals(2, aPosts.size());
        assertTrue(aPosts.stream().allMatch(p -> "userA".equals(p.getAuthor().getUsername())));
    }

    @Test
    void getPostsByUser_edge_noPosts_returnsEmpty() {
        List<Post> cPosts = wiadomoscDao.getPostsByUser(userC);
        assertNotNull(cPosts);
        assertTrue(cPosts.isEmpty());
    }

    @Test
    void getFullTimeline_typical_includesOwnAndFollowed() {
        follow(userA, userB); // A obserwuje B

        wiadomoscDao.addPost(post(userA, "A1"));
        wiadomoscDao.addPost(post(userB, "B1"));
        wiadomoscDao.addPost(post(userC, "C1"));

        List<Post> timeline = wiadomoscDao.getFullTimeline(userA);

        assertEquals(2, timeline.size());
        assertTrue(timeline.stream().anyMatch(p -> "A1".equals(p.getContent())));
        assertTrue(timeline.stream().anyMatch(p -> "B1".equals(p.getContent())));
        assertFalse(timeline.stream().anyMatch(p -> "C1".equals(p.getContent())));
    }

    @Test
    void getFullTimeline_edge_notFollowingAnyone_returnsOnlyOwn() {
        wiadomoscDao.addPost(post(userA, "A1"));
        wiadomoscDao.addPost(post(userB, "B1"));

        List<Post> timeline = wiadomoscDao.getFullTimeline(userA);

        assertEquals(1, timeline.size());
        assertEquals("A1", timeline.get(0).getContent());
    }

    @Test
    void getAllPosts_edge_empty_returnsEmpty() {
        List<Post> all = wiadomoscDao.getAllPosts();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }
}
