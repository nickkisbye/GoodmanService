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

    /**
     Tilføjer en employee (medarbejder) til en opgave igennem vores junk table.
     */
    public void createJob(Jobs obj) throws SQLException {
        sql = "INSERT INTO junc_jobs (fk_case, fk_employee) " +
                "VALUES (?, ?)";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getCaseId());
        preparedStatement.setInt(2, obj.getEmployeeId());
        preparedStatement.execute();
    }

    /**
     Finder alle jobs for en specifik opgave.
     */
    public ResultSet findByIdJobs(int id) throws SQLException {
        sql = "SELECT junc_jobs.fk_employee, junc_jobs.fk_case, junc_jobs.id, users.id, users.firstName, users.lastName FROM junc_jobs " +
                "INNER JOIN users ON junc_jobs.fk_employee = users.id " +
                "WHERE junc_jobs.fk_case = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    /**
     Fjerner et job fra en opgave.
     */
    public void deleteJob(int id) throws SQLException {
        sql = "DELETE FROM junc_jobs " +
                "WHERE id=?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    /**
    Henter en liste over alle medarbejdere (employees), som ikke allerede er sat på opgaven.
     */
    public ResultSet fetchEmployees(int id) throws SQLException {
        sql =   "SELECT users.id, users.firstName, users.lastName FROM users " +
                "LEFT JOIN junc_jobs ON users.id = junc_jobs.fk_employee " +
                "WHERE junc_jobs.fk_case IS NULL AND users.fk_role > 1 OR fk_case != ? AND users.fk_role > 1 " +
                "ORDER BY users.firstName";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    /**
    Henter en liste over alle tilbud (offers) som en bestemt kunde (customer) har.
     */
    public ResultSet customerOffers(int id) throws SQLException {
        sql = "SELECT id, description, price, startDate " +
                "FROM cases " +
                "WHERE fk_customer = ? " +
                "ORDER BY startDate";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    /**
    Henter en liste over alle informationer om en bestemt opgave.
     */
    public ResultSet customerInvoiceById(int id) throws SQLException {
        sql = "SELECT cases.id, cases.description, cases.price, cases.startDate, cases.endDate, users.firstName, users.lastName, users.email, users.address, users.phone, users.city, users.zip FROM cases " +
                "INNER JOIN users ON cases.fk_customer = users.id " +
                "WHERE cases.id = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    /**
    Henter færdige opgaver for en bestemt kunde (customer)
     */
    public ResultSet customerInvoice(int id) throws SQLException {
        sql = "SELECT id, price, description " +
                "FROM cases " +
                "WHERE fk_mode = 3 AND fk_customer = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }
}
