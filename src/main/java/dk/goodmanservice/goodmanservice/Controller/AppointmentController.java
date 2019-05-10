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

import java.sql.SQLException;

@Controller
public class AppointmentController {

    @Autowired
    private IService<Appointment> AS;

    @Autowired
    private IService<User> US;

    @GetMapping("/dashboard/appointments")
    public String appointments(Model model) throws SQLException {
        model.addAttribute("appointments", AS.fetch("all"));
        model.addAttribute("users", US.fetch("all"));
        return "dashboard/kalender";
    }

    @GetMapping("/dashboard/appointments/{id}")
    public String appointmentById(@PathVariable(value = "id") int id, Model model) throws SQLException {
        model.addAttribute("appointment", AS.findById(id));
        model.addAttribute("users", US.fetch("all"));
        return "dashboard/aftaler/aftaleEdit";
    }

    @PostMapping("/dashboard/appointment/edit")
    public String appointmentEdit(@ModelAttribute Appointment obj) throws SQLException {
        AS.edit(obj);
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable(value = "id") int id) throws SQLException {
        AS.delete(id);
        return "redirect:/dashboard/appointments";
    }

    @PostMapping("/dashboard/appointment/create")
    public String createAppointment(@ModelAttribute Appointment obj) throws SQLException {
        AS.create(obj);
        return "redirect:/dashboard/appointments";
    }
}
