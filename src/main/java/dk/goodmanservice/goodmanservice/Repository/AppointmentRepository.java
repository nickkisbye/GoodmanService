package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
@Component("AR")
public class AppointmentRepository implements IRepository<Appointment> {
    @Override
    public void create(Appointment obj) {

    }

    @Override
    public void edit(Appointment obj, String option) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ResultSet fetch(String option) {
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        return null;
    }
}
