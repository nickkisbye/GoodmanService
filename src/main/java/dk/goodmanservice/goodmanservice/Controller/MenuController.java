package dk.goodmanservice.goodmanservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/beregner")
    public String beregner() {
        return "beregner";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/employee";
    }

    @GetMapping("/dashboard/tilbud")
    public String tilbud() {
        return "dashboard/tilbud";
    }

    @GetMapping("/dashboard/opgaver")
    public String opgaver() {
        return "dashboard/opgaver";
    }

    @GetMapping("/dashboard/faerdigeopgaver")
    public String faerdigeopgaver() {
        return "dashboard/faerdigeopgaver";
    }

    @GetMapping("/dashboard/kalender")
    public String kalender() {
        return "dashboard/kalender";
    }

    @GetMapping("/dashboard/oekonomi")
    public String oekonomi() {
        return "dashboard/oekonomi";
    }

    @GetMapping("/dashboard/udlaeg")
    public String udlaeg() {
        return "dashboard/udlaeg";
    }

}
