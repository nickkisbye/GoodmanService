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

@Service
@Component("AS")
public class AppointmentService implements IService<Appointment> {

    @Autowired
    private IRepository<Appointment> AR;

    ResultSet resultSet;

    @Override
    public String create(Appointment obj) throws SQLException {
        AR.create(obj);
        return "success";
    }

    @Override
    public String edit(Appointment obj) throws SQLException {
        AR.edit(obj);
        return "success";
    }

    @Override
    public void delete(int id) throws SQLException {
        AR.delete(id);
    }

    @Override
    public List<Appointment> fetch(String option) throws SQLException {
        resultSet = AR.fetch(option);
        List<Appointment> appointmentList = new ArrayList<>();

        while (resultSet.next()) {
            Appointment appointment = new Appointment();
            User employee = new User();
            User customer = new User();
                appointment.setId(resultSet.getInt("id"));
                appointment.setDescription(resultSet.getString("description"));
                appointment.setDate(resultSet.getString("date"));
                appointment.setEmployeeId(resultSet.getInt("fk_employee"));
                appointment.setCustomerId(resultSet.getInt("fk_customer"));

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
        Appointment appointment = new Appointment();

        while (resultSet.next()) {
            appointment.setId(resultSet.getInt("id"));
            appointment.setDescription(resultSet.getString("description"));
            appointment.setDate(resultSet.getString("date"));
            appointment.setEmployeeId(resultSet.getInt("fk_employee"));
            appointment.setCustomerId(resultSet.getInt("fk_customer"));
        }
        return appointment;
    }
}