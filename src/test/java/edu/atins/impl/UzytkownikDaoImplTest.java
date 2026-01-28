package edu.atins.impl;

import edu.atins.dao.UzytkownikDao;
import edu.atins.model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UzytkownikDaoImplTest {

    private final UzytkownikDao uzytkownikDao = new UzytkownikDao() {

        private final Map<String, User> users = new HashMap<>();

        @Override
        public void addUser(User user) {
            if (user == null) throw new NullPointerException("user null");
            if (user.getUsername() == null) throw new IllegalArgumentException("username null");
            users.put(user.getUsername(), user);
        }

        @Override
        public User getUserByUsername(String username) {
            if (username == null) throw new NullPointerException("username null");
            return users.get(username);
        }
    };

    private User create(String username) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash("pass");
        return u;
    }

    // ===============================
    // TESTY addUser()
    // ===============================

    @Test
    void addUser_typical_addsCorrectly() {
        User jan = create("jan");

        uzytkownikDao.addUser(jan);

        User found = uzytkownikDao.getUserByUsername("jan");

        assertNotNull(found);
        assertEquals("jan", found.getUsername());
    }

    @Test
    void addUser_nullUser_throwsException() {
        assertThrows(NullPointerException.class, () -> uzytkownikDao.addUser(null));
    }

    @Test
    void addUser_nullUsername_throwsException() {
        User u = new User(); // brak setUsername
        u.setPasswordHash("x");

        assertThrows(IllegalArgumentException.class, () -> uzytkownikDao.addUser(u));
    }

    // ===============================
    // TESTY getUserByUsername()
    // ===============================

    @Test
    void getUser_existing_returnsUser() {
        User ola = create("ola");

        uzytkownikDao.addUser(ola);

        User found = uzytkownikDao.getUserByUsername("ola");

        assertNotNull(found);
        assertEquals("ola", found.getUsername());
    }

    @Test
    void getUser_notExisting_returnsNull() {
        assertNull(uzytkownikDao.getUserByUsername("brak"));
    }

    @Test
    void getUser_null_throwsException() {
        assertThrows(NullPointerException.class, () -> uzytkownikDao.getUserByUsername(null));
    }
}
