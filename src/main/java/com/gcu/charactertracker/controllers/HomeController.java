/**
 *version 1.0
 * 2026-05-20
 * @author  Alex G., Daniel L., and Jared J.
 * 
 * HomeController.java
 */
package com.gcu.charactertracker.controllers;

/**
 * Import statements for Spring Framework annotations and classes used in the HomeController.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class HomeController {
    
    /**
     * Handles GET requests to the root URL ("/") and returns the "home" view.
     *
     * @return The name of the view to be rendered, in this case, "home".
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

}
