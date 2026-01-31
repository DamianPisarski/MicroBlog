package edu.atins.controller;

import edu.atins.model.User;
import edu.atins.service.MicroblogService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.NoResultException;

import static spark.Spark.get;
import static spark.Spark.post;

public class AuthController {

    private final MicroblogService microblogService;
    private final FreeMarkerEngine freeMarker;

    public AuthController(MicroblogService microblogService, FreeMarkerEngine freeMarker) {
        this.microblogService = microblogService;
        this.freeMarker = freeMarker;
    }

    public void registerRoutes() {
        get("/login", (req, res) -> {
            res.type("text/html; charset=utf-8");
            java.util.Map<String, Object> model = new java.util.HashMap<>();
            model.put("title", "Login");
            model.put("msg", req.queryParams("msg"));

            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

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

            req.session(true).attribute("userId", user.getId());
            res.redirect("/panel");
            return null;
        });

        get("/register", (req, res) -> {
            res.type("text/html; charset=utf-8");
            java.util.Map<String, Object> model = new java.util.HashMap<>();
            model.put("title", "Register");
            model.put("msg", req.queryParams("msg"));

            return new ModelAndView(model, "register.ftl");
        }, freeMarker);

        post("/register", (req, res) -> {
            String username = trimToNull(req.queryParams("username"));
            String displayName = trimToNull(req.queryParams("displayName"));
            String email = trimToNull(req.queryParams("email"));
            String password = trimToNull(req.queryParams("password"));

            if (username == null || password == null) {
                res.redirect("/register?msg=Username%20i%20has%C5%82o%20s%C4%85%20wymagane");

                return null;
            }

            User user = new User();
            user.setUsername(username);
            user.setDisplayName(displayName);
            user.setEmail(email);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

            try {
                microblogService.registerUser(user);
            } catch (RuntimeException ex) {
                res.redirect("/register?msg=Nie%20uda%C5%82o%20si%C4%99%20utworzy%C4%87%20konta%20(mo%C5%BCe%20taki%20login/email%20ju%C5%BC%20istnieje)");

                return null;
            }

            req.session(true).attribute("userId", user.getId());
            res.redirect("/panel");

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
}
