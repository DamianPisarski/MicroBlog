package edu.atins.controller;

import edu.atins.model.Follower;
import edu.atins.model.User;
import edu.atins.service.MicroblogService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;

public class PanelController {

    private final FreeMarkerEngine freeMarker;
    private final MicroblogService microblogService;

    public PanelController(MicroblogService microblogService, FreeMarkerEngine freeMarker) {
        this.microblogService = microblogService;
        this.freeMarker = freeMarker;
    }

    public void registerRoutes() {
        before("/panel", (req, res) -> {
            if (req.session(false) == null || req.session(false).attribute("userId") == null) {
                res.redirect("/login?msg=Zaloguj%20si%C4%99%20aby%20uzyska%C4%87%20dost%C4%99p%20do%20panelu");
                halt();
            }
        });

        get("/panel", (req, res) -> {
            res.type("text/html; charset=utf-8");
            Map<String, Object> model = new HashMap<>();
            Long userId = req.session(false).attribute("userId");
            User user = microblogService.getUserById(userId);

            List<User> usersFollowersData = new ArrayList<>();
            List<Follower> followersId = microblogService.getFollowersByUserId(userId);

            followersId.forEach(follower -> {
                usersFollowersData.add(microblogService.getUserById(follower.getId()));
            });

            List<User> usersFollowedData = new ArrayList<>();
            List<Follower> followedId = microblogService.getFollowedByUserId(userId);

            followedId.forEach(follower -> {
                usersFollowedData.add(microblogService.getUserById(follower.getId()));
            });

            model.put("userData", microblogService.getUserById(userId));
            model.put("userPosts", microblogService.getPostsByUserId(userId));
            model.put("userTimeline", microblogService.getUserTimeline(user));
            model.put("userFollowers", usersFollowersData);
            model.put("userFollowed", usersFollowedData);
            
            return new ModelAndView(model, "panel.ftl");
        }, freeMarker);
    }
}
