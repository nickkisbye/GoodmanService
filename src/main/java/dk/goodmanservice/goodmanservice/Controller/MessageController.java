package dk.goodmanservice.goodmanservice.Controller;


import dk.goodmanservice.goodmanservice.Model.Message;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class MessageController {

    @Autowired
    private IService<Message> MS;

    @PostMapping("/message/create")
    public String createMsg(@ModelAttribute Message message, Model model) {
        try {
            MS.create(message);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "redirect:/dashboard/employee";
    }

}
