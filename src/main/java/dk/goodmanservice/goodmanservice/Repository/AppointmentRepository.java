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
    public ResultSet fetch(String option, int id) throws SQLException {
        sql = "SELECT appointments.*, group_concat(users.firstName, users.lastName) AS name " +
                "FROM appointments, users " +
                "WHERE appointments.fk_employee = users.id OR appointments.fk_customer = users.id " +
                "GROUP BY appointments.id ";
        switch (option) {
            case "customer":
                sql += "WHERE fk_costumer = '" + id + "' ";
                break;
            case "employee":
                sql += "WHERE fk_employee '" + id + "' ";
                break;
        }

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT * FROM appointments WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
