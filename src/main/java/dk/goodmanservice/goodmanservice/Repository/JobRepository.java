package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Jobs;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Calendar;

@Repository
public class JobRepository {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public JobRepository() throws SQLException {
        this.con = DriverManager.getConnection(
                "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                "goodmanservicedb",
                "Ly02_scr-4ds");
    }

    public void createJob(Jobs obj) throws SQLException {
        sql = "INSERT INTO junc_jobs (fk_case, fk_employee) VALUES (?, ?)";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getCaseId());
        preparedStatement.setInt(2, obj.getEmployeeId());
        preparedStatement.execute();
    }


    public ResultSet findByIdJobs(int id) throws SQLException {
        sql = "SELECT * FROM junc_jobs " +
                "INNER JOIN users ON junc_jobs.fk_employee = users.id " +
                "WHERE junc_jobs.fk_case = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public void deleteJob(int id) throws SQLException {
        sql = "DELETE FROM junc_jobs WHERE id=?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public ResultSet fetchEmployees(int id) throws SQLException {
        sql =   "SELECT * FROM users " +
                "LEFT JOIN junc_jobs ON users.id = junc_jobs.fk_employee " +
                "WHERE junc_jobs.fk_case IS NULL OR fk_case != ? ";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public ResultSet customerOffers(int id) throws SQLException {
        sql = "SELECT id, description, price, startDate FROM cases WHERE fk_mode = 1 AND fk_customer = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    public ResultSet customerInvoiceById(int id) throws SQLException {
        sql = "SELECT * FROM cases INNER JOIN users ON cases.fk_customer = users.id WHERE cases.id = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    public ResultSet customerInvoice(int id) throws SQLException {
        sql = "SELECT id, price, description FROM cases WHERE fk_mode = 3 AND fk_customer = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }
}
