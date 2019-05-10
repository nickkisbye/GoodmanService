package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Message;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private IService<Message> MS;

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

    @GetMapping("/dashboard/employee")
    public String employee() {
        MS.fetch("latest-5", )
        return "dashboard/employee";
    }

    @GetMapping("/dashboard/customer")
    public String customer() {
        return "dashboard/customer";
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
