package dk.goodmanservice.goodmanservice.Controller;


import dk.goodmanservice.goodmanservice.Model.Message;
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
public class MessageController {

    @Autowired
    private IService<Message> MS;

    @PostMapping("/message/create")
    public String createMsg(@ModelAttribute Message message, RedirectAttributes redirect) {
        try {
            MS.create(message);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/employee";
    }

    @GetMapping("/message/delete/{id}")
    public String deleteMsg(@PathVariable("id") int id, RedirectAttributes redirect) {
        try {
            MS.delete(id);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/employee";
    }

}
