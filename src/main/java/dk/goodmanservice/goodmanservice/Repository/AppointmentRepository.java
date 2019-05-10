package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Component("AR")
public class AppointmentRepository implements IRepository<Appointment> {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public AppointmentRepository() {
        try {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Appointment obj) throws SQLException {
        sql = "INSERT INTO appointments (description, date, fk_employee, fk_costumer) " +
                "VALUES (?, ?, ?, ?)";

            preparedStatement = con.prepareStatement(sql);
            executeAppointment(obj);
    }

    @Override
    public void edit(Appointment obj) throws SQLException {
        sql = "UPDATE appointments " +
                "SET description = ?, date = ?, fk_employee = ?, fk_customer = ? " +
                "WHERE id = '" + obj.getId() + "'";

            preparedStatement = con.prepareStatement(sql);
            executeAppointment(obj);
    }

    private void executeAppointment(Appointment obj) throws SQLException {
        preparedStatement.setString(1, obj.getDescription());
        preparedStatement.setString(2, obj.getDate());
        preparedStatement.setInt(3, obj.getEmployeeId());
        preparedStatement.setInt(4, obj.getCustomerId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM appointments WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {
        sql = "SELECT appointments.*, u1.firstName AS eFirstName, u1.lastName AS eLastName, u2.firstName AS cFirstName, u2.lastName AS cLastName FROM appointments " +
                "LEFT JOIN users as u1 ON appointments.fk_employee = u1.id " +
                "LEFT JOIN users as u2 ON appointments.fk_customer = u2.id " +
                "ORDER BY appointments.date";

        preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT appointments.*, u1.firstName, u1.lastName, u2.firstName, u2.lastName FROM appointments " +
                "LEFT JOIN users as u1 ON appointments.fk_customer = u1.id " +
                "LEFT JOIN users as u2 ON appointments.fk_employee = u2.id " +
                "WHERE  appointments.fk_employee = " + id +
                " OR appointments.fk_customer = " + id;

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
