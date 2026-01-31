package edu.atins.controller;

import edu.atins.service.MicroblogService;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;

public class PanelController {

    private final FreeMarkerEngine freeMarker;

    public PanelController(MicroblogService microblogService, FreeMarkerEngine freeMarker) {
        this.freeMarker = freeMarker;
    }

    public void registerRoutes() {
        before("/panel", (req, res) -> {
            if (req.session(false) == null || req.session(false).attribute("username") == null) {
                res.redirect("/login?msg=Zaloguj%20si%C4%99%20aby%20uzyska%C4%87%20dost%C4%99p%20do%20panelu");
                halt();
            }
        });

        get("/panel", (req, res) -> {
            res.type("text/html; charset=utf-8");
            Map<String, Object> model = new HashMap<>();
            model.put("title", "User Panel");
            model.put("username", req.session(false).attribute("username"));

            return new ModelAndView(model, "panel.ftl");
        }, freeMarker);
    }
}
