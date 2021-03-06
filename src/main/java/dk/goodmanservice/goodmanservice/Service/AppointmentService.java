package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lavet af Markus
 */

@Service
@Component("AS")
public class AppointmentService implements IService<Appointment> {

    @Autowired
    private IRepository<Appointment> AR;

    @Autowired
    private Validation V;

    private ResultSet resultSet;

    /**
     * AppointmentService benytter sig af vores CRUD Interface og sørger for inmplementeringen af disse,
     * det hele fungerer meget standardiseret og er meget lig vores andre Serviceklasser, denne benytter sig også
     * af Validation til tjek på indkomne værdier.
     */

    @Override
    public String create(Appointment obj) throws SQLException {
        String checkSum = V.validateAppointment(obj);
        if(checkSum.equals("1")) {
            AR.create(obj);
            return "OPRETTET";
        }
        return checkSum;
    }

    @Override
    public String edit(Appointment obj) throws SQLException {
        String checkSum = V.validateAppointment(obj);
        if(checkSum.equals("1")) {
            AR.edit(obj);
            return "REDIGERET";
        }
        return checkSum;
    }

    @Override
    public String delete(int id) throws SQLException {
        AR.delete(id);
        return "SLETTET";
    }

    @Override
    public List<Appointment> fetch(String option) throws SQLException {
        resultSet = AR.fetch(option);
        List<Appointment> appointmentList = new ArrayList<>();

        while (resultSet.next()) {
            Appointment appointment = appointmentFiller();
            User employee = new User();
            User customer = new User();

                employee.setFirstName(resultSet.getString("eFirstName"));
                employee.setLastName(resultSet.getString("eLastName"));

                customer.setFirstName(resultSet.getString("cFirstName"));
                customer.setLastName(resultSet.getString("cLastName"));

                appointment.setEmployee(employee);
                appointment.setCustomer(customer);

                appointmentList.add(appointment);
            }
        return appointmentList;
    }

    @Override
    public Appointment findById(int id) throws SQLException {
        resultSet = AR.findById(id);
        Appointment appointment = null;

        while (resultSet.next()) {
            appointment = appointmentFiller();
        }
        return appointment;
    }

    /**
     * Reducerer redundans på opsætning af objekter.
     */

    private Appointment appointmentFiller() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getInt("id"));
        appointment.setDescription(resultSet.getString("description"));
        appointment.setDate(resultSet.getString("date"));
        appointment.setTime(resultSet.getString("time"));
        appointment.setEmployeeId(resultSet.getInt("fk_employee"));
        appointment.setCustomerId(resultSet.getInt("fk_customer"));

        return appointment;
    }
}