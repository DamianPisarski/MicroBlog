package edu.atins;

import static spark.Spark.*;

import edu.atins.controller.AuthController;
import edu.atins.service.MicroblogService;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App {
    public static void main(String[] args) {

        // Config
        FileSystemXmlApplicationContext spring =
            new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");

        Runtime.getRuntime().addShutdownHook(new Thread(spring::close));

        // Classes
        MicroblogService microblogService = spring.getBean(MicroblogService.class);

        // Routes
        new AuthController(microblogService).registerRoutes();

        // Test route
        get("/hello", (req, res) -> "Hello MicroBlog!");
    }
}
