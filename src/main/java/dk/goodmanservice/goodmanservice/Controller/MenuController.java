package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Calculator;
import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.Message;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.CalcService;
import dk.goodmanservice.goodmanservice.Service.IService;
import dk.goodmanservice.goodmanservice.Service.JobService;
import dk.goodmanservice.goodmanservice.Service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class MenuController {

    @Autowired
    private StatisticService SS;

    @Autowired
    private IService<Message> MS;

    @Autowired
    private IService<User> US;

    @Autowired
    private CalcService CS;

    @Autowired
    private JobService JS;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard/employee")
    public String employee(Model model) {

        try {
            model.addAttribute("messages", MS.fetch("latest-10"));
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/employee";
    }

    @GetMapping("/dashboard/customer")
    public String customer(HttpSession session, Model model) {
        int id = (int) session.getAttribute("id");
        try {
            model.addAttribute("offers", JS.customerOffers(id));
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/customer";
    }

    @GetMapping("/dashboard/kalender")
    public String kalender() {
        return "redirect:/dashboard/appointments";
    }

    @GetMapping("/dashboard/oekonomi")
    public String oekonomi(Model model) {
        try {
            model.addAttribute("total", SS.fetch("total"));
            model.addAttribute("monthly", SS.fetch("monthly"));
            model.addAttribute("yearly", SS.fetch("yearly"));
            model.addAttribute("average", SS.fetch("average"));
            model.addAttribute("employeelist", SS.fetch("employee-top10"));
            model.addAttribute("employee", SS.fetch("top-employee"));
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

        return "dashboard/oekonomi";
    }

    @GetMapping("/dashboard/udlaeg")
    public String udlaeg() {
        return "redirect:/dashboard/expenses";
    }

    @GetMapping("/dashboard/kunder")
    public String kunder(Model model) {
        try {
            model.addAttribute("customers", US.fetch("customers"));
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/customerlist";
    }

    @GetMapping("/dashboard/kunder/{id}")
    public String kundeView(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("kunde", US.findById(id));
            model.addAttribute("jobs", JS.customerOffers(id));
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/kunderView";
    }

    @GetMapping("/beregner")
    public String calcView(Model model) {
        model.addAttribute("calcing", false);
        return "beregner";
    }

    @PostMapping("/beregner")
    public String calcCalc(Model model, @ModelAttribute Calculator obj) {
        model.addAttribute("calcing", true);
        model.addAttribute("price", CS.iCalc(obj));
        return "beregner";
    }
}
