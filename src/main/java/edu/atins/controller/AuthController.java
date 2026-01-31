package edu.atins.controller;

import edu.atins.model.User;
import edu.atins.service.MicroblogService;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.NoResultException;

import static spark.Spark.get;
import static spark.Spark.post;

public class AuthController {

    private final MicroblogService microblogService;

    public AuthController(MicroblogService microblogService) {
        this.microblogService = microblogService;
    }

    public void registerRoutes() {
        get("/login", (req, res) -> {
            res.type("text/html; charset=utf-8");
            String msg = req.queryParams("msg");
            return htmlPage("Login", "<h2>Login</h2>" +
                    (msg != null ? ("<p style='color:#b00'>" + escape(msg) + "</p>") : "") +
                    "<form method='post' action='/login'>" +
                    "<label>Username</label><br/>" +
                    "<input name='username'/> <br/>" +
                    "<label>Password</label><br/>" +
                    "<input type='password' name='password'/> <br/><br/>" +
                    "<button type='submit'>Login</button>" +
                    "</form>" +
                    "<p>Nie masz konta? <a href='/register'>Zarejestruj się</a></p>");
        });

        post("/login", (req, res) -> {
            String username = trimToNull(req.queryParams("username"));
            String password = trimToNull(req.queryParams("password"));

            if (username == null || password == null) {
                res.redirect("/login?msg=Brak%20loginu%20lub%20has%C5%82a");
                return null;
            }

            User user;
            try {
                user = microblogService.getUserByUsername(username);
            } catch (NoResultException ex) {
                res.redirect("/login?msg=Nieprawid%C5%82owy%20login%20lub%20has%C5%82o");
                return null;
            } catch (RuntimeException ex) {
                res.redirect("/login?msg=B%C5%82%C4%85d%20podczas%20logowania");
                return null;
            }

            if (user == null || user.getUsername() == null) {
                res.redirect("/login?msg=Nieprawid%C5%82owy%20login%20lub%20has%C5%82o");
                return null;
            }

            String hash = user.getPasswordHash();
            boolean ok = hash != null && BCrypt.checkpw(password, hash);
            if (!ok) {
                res.redirect("/login?msg=Nieprawid%C5%82owy%20login%20lub%20has%C5%82o");
                return null;
            }

            req.session(true).attribute("username", user.getUsername());
            res.redirect("/hello");
            return null;
        });

        get("/register", (req, res) -> {
            res.type("text/html; charset=utf-8");
            String msg = req.queryParams("msg");
            return htmlPage("Register", "<h2>Register</h2>" +
                    (msg != null ? ("<p style='color:#b00'>" + escape(msg) + "</p>") : "") +
                    "<form method='post' action='/register'>" +
                    "<label>Username</label><br/>" +
                    "<input name='username'/> <br/>" +
                    "<label>Email</label><br/>" +
                    "<input name='email'/> <br/>" +
                    "<label>Password</label><br/>" +
                    "<input type='password' name='password'/> <br/><br/>" +
                    "<button type='submit'>Register</button>" +
                    "</form>" +
                    "<p>Masz konto? <a href='/login'>Zaloguj się</a></p>");
        });

        post("/register", (req, res) -> {
            String username = trimToNull(req.queryParams("username"));
            String email = trimToNull(req.queryParams("email"));
            String password = trimToNull(req.queryParams("password"));

            if (username == null || password == null) {
                res.redirect("/register?msg=Username%20i%20has%C5%82o%20s%C4%85%20wymagane");
                return null;
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

            try {
                microblogService.registerUser(user);
            } catch (RuntimeException ex) {
                res.redirect("/register?msg=Nie%20uda%C5%82o%20si%C4%99%20utworzy%C4%87%20konta%20(mo%C5%BCe%20taki%20login/email%20ju%C5%BC%20istnieje)");
                return null;
            }

            req.session(true).attribute("username", user.getUsername());
            res.redirect("/hello");
            return null;
        });

        post("/logout", (req, res) -> {
            if (req.session(false) != null) {
                req.session().invalidate();
            }
            res.redirect("/hello");
            return null;
        });
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String escape(String s) {
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String htmlPage(String title, String body) {
        return "<!doctype html>" +
                "<html><head><meta charset='utf-8'><title>" + escape(title) + "</title></head>" +
                "<nav><a href='/hello'>Home</a> | <a href='/login'>Login</a> | <a href='/register'>Register</a></nav>" +
                "<hr/>" + body +
                "</body></html>";
    }
}
