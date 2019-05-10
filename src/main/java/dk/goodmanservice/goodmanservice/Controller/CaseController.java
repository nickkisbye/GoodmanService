package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.SimpleAttributeSet;
import java.sql.SQLException;

@Controller
public class CaseController {

    @Autowired
    private IService<Case> CS;



    @GetMapping("/dashboard/tilbud")
    public String offer() {

        return "dashboard/offer";
    }
    @GetMapping("/dashboard/tilbud/ny")
    public String createOffer(Model model, Case cases) {
        return "dashboard/offerCreate";
    }
    @PostMapping("/dashboard/tilbud/ny")
    public String createOfferForm(Model model, Case cases) {
        try {
            model.addAttribute("error", CS.create(cases));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "dashboard/offerCreate";
    }

    @GetMapping("/dashboard/opgaver")
    public String cases() {

        return "dashboard/cases";
    }

    @GetMapping("/dashboard/faerdigeopgaver")
    public String done() {

        return "dashboard/done";
    }

}
