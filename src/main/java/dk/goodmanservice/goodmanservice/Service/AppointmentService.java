package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Appointment;
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
    public List<Appointment> fetch(String option, int id) throws SQLException {
        resultSet = AR.fetch(option, id);
        List<Appointment> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            Appointment appointment = new Appointment();

        }
        return null;
    }

    @Override
    public Appointment findById(int id) throws SQLException {
        return null;
    }
}