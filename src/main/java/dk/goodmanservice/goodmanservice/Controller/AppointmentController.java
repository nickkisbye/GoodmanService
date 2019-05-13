package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Appointment;
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

import java.sql.SQLException;

@Controller
public class AppointmentController {

    @Autowired
    private IService<Appointment> AS;

    @Autowired
    private IService<User> US;

    @GetMapping("/dashboard/appointments")
    public String appointments(Model model) {
        try {
            model.addAttribute("appointments", AS.fetch("all"));
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "dashboard/kalender";
    }

    @GetMapping("/dashboard/appointments/edit/{id}")
    public String appointmentById(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("appointment", AS.findById(id));
            model.addAttribute("appointments", AS.fetch("all"));
            model.addAttribute("users", US.fetch("all"));
            model.addAttribute("edit", true);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "dashboard/kalender";
    }

    @PostMapping("/dashboard/appointment/edit")
    public String appointmentEdit(@ModelAttribute Appointment obj, RedirectAttributes model) {
        try {
            model.addFlashAttribute("expense", AS.edit(obj));
            model.addFlashAttribute("edit", false);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable("id") int id, Model model) {
        try {
            AS.delete(id);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/create")
    public String createAppointment(@ModelAttribute Appointment obj, Model model) {
        try {
            AS.create(obj);
        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "error";
        }
        return "redirect:/dashboard/appointments";
    }
}
