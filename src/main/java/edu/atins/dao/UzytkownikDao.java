package edu.atins.dao;

import edu.atins.model.User;

public interface UzytkownikDao {
    // Pobranie użytkownika po loginie
    User getUserByUsername(String username);

    // Pobranie użytkownika po ID
    User getUserById(Long id);

    // Dodanie nowego użytkownika
    void addUser(User user);
}
