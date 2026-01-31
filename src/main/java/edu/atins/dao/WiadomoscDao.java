package edu.atins.dao;

import edu.atins.model.Post;
import edu.atins.model.User;
import java.util.List;

public interface WiadomoscDao {
    // Pobranie wszystkich wiadomości konkretnego użytkownika
    List<Post> getPostsByUser(User user);

    // Pobranie wszystkich wiadomości konkretnego użytkownika po jego ID
    List<Post> getPostsByUserId(Long id);

    // Pobranie wszystkich wiadomości mojego i obserwowanych użytkowników
    List<Post> getFullTimeline(User user);

    // Pobranie wszystkich wiadomości od wszystkich użytkowników
    List<Post> getAllPosts();

    // Dodanie wiadomości
    void addPost(Post post);
}
