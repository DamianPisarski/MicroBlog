package edu.atins;

import static spark.Spark.*;

import edu.atins.controller.AuthController;
import edu.atins.controller.PanelController;
import edu.atins.service.MicroblogService;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        
        // FreeMarker config (templates in: src/main/resources/templates)
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(App.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        FreeMarkerEngine freeMarker = new FreeMarkerEngine(cfg);

        // Config
        FileSystemXmlApplicationContext spring =
            new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");

        Runtime.getRuntime().addShutdownHook(new Thread(spring::close));

        // Classes
        MicroblogService microblogService = spring.getBean(MicroblogService.class);

        // Routes
        new AuthController(microblogService, freeMarker).registerRoutes();
        new PanelController(microblogService, freeMarker).registerRoutes();

        // Test route
        get("/hello", (req, res) -> "Hello MicroBlog!");
    }
}
