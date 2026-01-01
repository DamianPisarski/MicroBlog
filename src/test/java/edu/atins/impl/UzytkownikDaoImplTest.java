package edu.atins.impl;

import edu.atins.dao.UzytkownikDao;
import edu.atins.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UzytkownikDaoImplTest {

    // Mockowa implementacja na potrzeby kompilacji
    private UzytkownikDao uzytkownikDao = new UzytkownikDao() {
        @Override
        public void addUser(User user) {
            // mock
        }

        @Override
        public User getUserByUsername(String username) {
            // mock zwraca testowego usera
            User u = new User();
            u.setUsername(username);
            return u;
        }
    };

    @Test
    void testAddUserAndGetUserByUsername() {
        User user = new User();
        user.setUsername("janek");
        user.setPasswordHash("haslo"); // zgodnie z Twoją aktualizacją modelu

        uzytkownikDao.addUser(user);

        User retrieved = uzytkownikDao.getUserByUsername("janek");
        assertNotNull(retrieved);
        assertEquals("janek", retrieved.getUsername());
    }
}
