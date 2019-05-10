package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.IService;
import dk.goodmanservice.goodmanservice.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private IService<User> US;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        if (loginService.login(user)) {
            session.setAttribute("email", user.getEmail());
            session.setAttribute("firstname", user.getFirstName());
            session.setAttribute("lastname", user.getLastName());
            session.setAttribute("phone", user.getPhoneNumber());
            session.setAttribute("role", user.getRoleName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("id", user.getId());

            if (user.getRoleName().equals("customer")) {
                return "redirect:/dashboard/customer";
            } else {
                return "redirect:/dashboard/employee";
            }

        } else {
            session.invalidate();
            return "index";
        }
    }

    @GetMapping("/dashboard/brugere")
    public String brugere() {
        return "dashboard/brugere";
    }

    @GetMapping("/dashboard/kunder")
    public String kunder() {
        return "dashboard/kunder";
    }



}
