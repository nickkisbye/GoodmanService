package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.CaseService;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class CaseController {

    @Autowired
    private IService<Case> CS;

    @Autowired
    private IService<User> US;

    /**
     * TILBUD ONLY
     **/
    @GetMapping("/dashboard/tilbud")
    public String offer(Model model) throws SQLException { // husk catch
        model.addAttribute("case", CS.fetch("offer"));
        model.addAttribute("users", US.fetch("customers"));
        model.addAttribute("edit", false);

        return "dashboard/Cases/offer";
    }
    /**
     * OPGAVER ONLY
     **/
    @GetMapping("/dashboard/opgaver")
    public String cases(Model model) throws SQLException {
        model.addAttribute("case", CS.fetch("offer"));
        model.addAttribute("users", US.fetch("customers"));
        model.addAttribute("edit", false);
        return "dashboard/Cases/cases";
    }
    /**
     * FÆRDIGE OPGAVER ONLY
     **/
    @GetMapping("/dashboard/faerdigeopgaver")
    public String done(Model model) throws SQLException {
        model.addAttribute("case", CS.fetch("offer"));
        model.addAttribute("users", US.fetch("customers"));
        model.addAttribute("edit", false);
        return "dashboard/Cases/done";
    }

    /**
     * ALL COMBINED
     **/
    @GetMapping("/dashboard/TOF/vis/{id}")
    public String viewTOF(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("case", CS.findById(id));
        return "dashboard/Cases/vis";
    }

    @GetMapping("/dashboard/TOF/redigere/{id}")
    public String editTOF(@PathVariable("id") int id,  Model model) throws SQLException { // husk catch
        model.addAttribute("case", CS.fetch("offer"));
        model.addAttribute("findById", CS.findById(id));
        model.addAttribute("users", US.fetch("customers"));
        model.addAttribute("edit", true);

        switch (CS.findById(id).getMode()) {
            case 1:
                return "dashboard/Cases/offer";
            case 2:
                return "dashboard/Cases/cases";
            case 3:
                return "dashboard/Cases/done";
            default:
                return "dashboard/Cases/no";
        }

    }

    @PostMapping("/dashboard/TOF/redigere/{id}")
    public String editTOFFORM(@PathVariable("id") int id, @ModelAttribute Case obj, RedirectAttributes ra) throws SQLException { // husk catch
        ra.addFlashAttribute("msg", CS.edit(obj));
        ra.addFlashAttribute("edit", false);
        return "redirect:/dashboard/tilbud";
    }

    @PostMapping("/dashboard/TOF/upgrade/{id}")
    public String upgradeTOF(@PathVariable("id") int id, @ModelAttribute Case obj, RedirectAttributes ra) throws SQLException { // husk catch

        ra.addFlashAttribute("msg", CS.edit(obj));
        ra.addFlashAttribute("edit", false);

        switch (CS.findById(id).getMode()) {
            case 1:
                return "redirect:/dashboard/tilbud";
            case 2:
                return "redirect:/dashboard/opgaver";
            case 3:
                return "redirect:/dashboard/faerdigeopgaver";
            default:
                return "redirect:/dashboard/error";
        }
    }

    @PostMapping("/dashboard/TOF/slet/{id}")
    public String deleteTOF(@PathVariable("id") int id, RedirectAttributes ra) throws SQLException { // husk catch
        CS.delete(id);

        return "redirect:/dashboard/tilbud";
    }
    @PostMapping("/dashboard/TOF")
    public String createTOF(Case cases, RedirectAttributes ra) throws SQLException { // husk catch
        ra.addFlashAttribute("error", CS.create(cases));

        return "redirect:/dashboard/tilbud";
    }







}
