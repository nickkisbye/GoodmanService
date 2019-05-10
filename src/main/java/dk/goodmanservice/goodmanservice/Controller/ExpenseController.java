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

import java.sql.SQLException;

@Controller
public class ExpenseController {

    @Autowired
    private IService<Expense> ES;

    @Autowired
    private IService<User> US;

    @GetMapping("/dashboard/expenses")
    public String expenses(Model model) throws SQLException {
        model.addAttribute("expenses", ES.fetch("all"));
        model.addAttribute("users", US.fetch("all"));
        model.addAttribute("edit", false);
        return "dashboard/udlaeg";
    }

    @GetMapping("/dashboard/expenses/{id}")
    public String expenseById(@PathVariable(value = "id") int id, Model model) throws SQLException {
        model.addAttribute("expense", ES.findById(id));
        model.addAttribute("users", US.fetch("all"));
        return "dashboard/udlaeg/udlaegEdit";
    }

    @PostMapping("/dashboard/expenses/edit")
    public String editExpense(@ModelAttribute Expense obj) throws SQLException {
        ES.edit(obj);
        return "redirect:/dashboard/expenses";
    }

    @PostMapping("/dashboard/expenses/delete/{id}")
    public String deleteExpense(@PathVariable(value = "id") int id) throws SQLException {
        ES.delete(id);
        return "redirect:/dashboard/expenses";
    }

    @PostMapping("/dashboard/expenses/create")
    public String createExpense(@ModelAttribute Expense obj) throws SQLException {
        ES.create(obj);
        return "redirect:/dashboard/expenses";
    }
}
