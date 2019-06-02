package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Expense;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Lavet af Markus
 */

@Controller
public class ExpenseController {

    @Autowired
    private IService<Expense> ES;

    @Autowired
    private IService<User> US;

    @GetMapping("/dashboard/expenses")
    public String expenses(Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            if((int) session.getAttribute("level") > 70) {
                model.addAttribute("expenses", ES.fetch("all"));
            } else {
                model.addAttribute("expenses", ES.fetch(session.getAttribute("id").toString()));
            }
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/udlaeg";
    }

    @GetMapping("/dashboard/expenses/edit/{id}")
    public String expenseById(@PathVariable("id") int id, Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            model.addAttribute("expense", ES.findById(id));
            if((int) session.getAttribute("level") > 70) {
                model.addAttribute("expenses", ES.fetch("all"));
            } else {
                model.addAttribute("expenses", ES.fetch(session.getAttribute("id").toString()));
            }
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", true);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/udlaeg";
    }

    @PostMapping("/dashboard/expenses/edit")
    public String editExpense(@ModelAttribute Expense obj, RedirectAttributes redirect) {
        try {
            String msg = ES.edit(obj);
            redirect.addFlashAttribute("msg", msg);

            if(!msg.equals("REDIGERET")) {
                return "redirect:/dashboard/expenses/edit/" + obj.getId();
            }
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/expenses";
    }

    @PostMapping("/dashboard/expenses/delete/{id}")
    public String deleteExpense(@PathVariable("id") int id, RedirectAttributes redirect) {
        try {
            redirect.addFlashAttribute("msg", ES.delete(id));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/expenses";
    }

    @PostMapping("/dashboard/expenses/create")
    public String createExpense(@ModelAttribute Expense obj, RedirectAttributes redirect) {
        try {
            redirect.addFlashAttribute("msg", ES.create(obj));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/expenses";
    }
}
