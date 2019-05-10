package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

@Controller
public class AppointmentController {

    @Autowired
    private IService<Appointment> AS;

    @GetMapping("/appointments")
    public String appointments(Model model) throws SQLException {
        model.addAttribute("appointments", AS.fetch("All"));
        return "dashboard/kalender";
    }

    @GetMapping("/appointments/{id}")
    public String appointments(@PathVariable(value = "id") int id, Model model) throws SQLException {
        model.addAttribute("appointments", AS.findById(id));
        return "dashboard/kalender";
    }
}
