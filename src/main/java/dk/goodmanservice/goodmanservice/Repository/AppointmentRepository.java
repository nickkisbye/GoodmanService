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
    public void create(Appointment obj) {
        sql = "INSERT INTO appointments (description, date, fk_employee, fk_costumer) " +
                "VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(sql);
            executeAppointment(obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Appointment obj, String option) {
        sql = "UPDATE appointments " +
                "SET description = ?, date = ?, fk_employee = ?, fk_customer = ? " +
                "WHERE id = '" + obj.getId() + "'";
        try {
            preparedStatement = con.prepareStatement(sql);
            executeAppointment(obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeAppointment(Appointment obj) throws SQLException {
        preparedStatement.setString(1, obj.getDescription());
        preparedStatement.setString(2, obj.getDate());
        preparedStatement.setInt(3, obj.getEmployeeId());
        preparedStatement.setInt(4, obj.getCustomerId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) {
        sql = "DELETE FROM appointments WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet fetch(String option) {
        switch (option) {
            case "customers":
                sql = "SELECT * FROM appointments WHERE fk_employee = 1";
                break;
            case "employees":
                sql = "SELECT * FROM appointments WHERE fk_customer > 2";
                break;
            case "cases":
                sql = "SELECT * FROM appointments";
                break;
        }

        try {
            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        sql = "SELECT * FROM appointments WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
