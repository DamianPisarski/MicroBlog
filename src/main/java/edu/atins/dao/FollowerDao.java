package edu.atins.dao;

import edu.atins.model.User;

public interface FollowerDao {
    // Dodanie użytkownika do obserwowanych
    void follow(User follower, User followed);

    // Usunięcie użytkownika z obserwowanych
    void unfollow(User follower, User followed);

    // Sprawdzenie, czy użytkownik jest obserwowany
    boolean isFollowing(User follower, User followed);
}
