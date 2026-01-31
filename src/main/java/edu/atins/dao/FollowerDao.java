package edu.atins.dao;

import edu.atins.model.User;

import java.util.List;
import edu.atins.model.Follower;
public interface FollowerDao {
    // Dodanie użytkownika do obserwowanych
    void follow(User follower, User followed);

    // Usunięcie użytkownika z obserwowanych
    void unfollow(User follower, User followed);

    // Sprawdzenie, czy użytkownik jest obserwowany
    boolean isFollowing(User follower, User followed);

    // Pobranie listy obserwujących danego użytkownika
    List<Follower> getFollowers(Long userId);

    // Pobranie listy użytkowników obserwowanych przez danego użytkownika
    List<Follower> getFollowed(Long userId);
}
