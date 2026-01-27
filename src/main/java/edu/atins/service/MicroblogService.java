package edu.atins.service;

import java.util.List;
import edu.atins.model.User;
import edu.atins.model.Post;

public interface MicroblogService {
    // Rejestracja i pobieranie danych uzytkownika
    void registerUser(User user);
    User getUserByUsername(String username);

    // Relacje miedzy uzytkownikami
    void followUser(User follower, User followed);
    void unfollowUser(User follower, User followed);
    boolean isFollowing(User follower, User followed);

    // Logika wpisow
    void createPost(Post post);
    List<Post> getUserTimeline(User user);
    List<Post> getAllPosts();
    List<Post> getPostsByAuthor(User author);
}