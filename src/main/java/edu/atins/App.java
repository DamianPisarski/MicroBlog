package edu.atins;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello MicroBlog!");
    }
}
