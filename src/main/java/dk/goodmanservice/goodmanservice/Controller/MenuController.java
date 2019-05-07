package dk.goodmanservice.goodmanservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/calc")
    public String calc() {
        return "calc";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
