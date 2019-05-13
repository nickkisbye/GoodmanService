package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Case;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Repository
@Component("CR")
public class CaseRepository implements IRepository<Case> {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;
    private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public CaseRepository() throws SQLException {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
    }


    @Override
    public void create(Case obj) throws SQLException {

        sql = "INSERT INTO cases (description, price, creationDate, startDate, endDate, fk_mode, fk_customer) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Calendar cal = Calendar.getInstance();
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, obj.getDescription());
        preparedStatement.setInt(2, obj.getPrice());
        preparedStatement.setString(3, sdf.format(cal.getTime()));
        preparedStatement.setString(4, obj.getStartDate());
        preparedStatement.setString(5, obj.getEndDate());
        preparedStatement.setInt(6, obj.getMode());
        preparedStatement.setInt(7, obj.getCustomerId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Case obj) throws SQLException {

        sql = "UPDATE cases SET description=?, price=?, startDate=?, endDate=?, fk_mode=?, fk_customer=? WHERE id = '" + obj.getId() + "'";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, obj.getDescription());
        preparedStatement.setInt(2, obj.getPrice());
        preparedStatement.setString(3, obj.getStartDate());
        preparedStatement.setString(4, obj.getEndDate());
        preparedStatement.setInt(5, obj.getMode());
        preparedStatement.setInt(6, obj.getCustomerId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM cases WHERE id=?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {

        switch (option) {
            case "offer":
                sql = "SELECT * FROM cases " +
                        "LEFT JOIN users ON cases.fk_customer = users.id " +
                        "LEFT JOIN roles ON users.fk_role = roles.id " +
                        "WHERE fk_mode = 1";
                break;
            case "cases":
                sql = "SELECT * FROM cases WHERE fk_mode = 2";
                break;
            case "finished":
                sql = "SELECT * FROM cases WHERE fk_mode = 3";
                break;
        }

        preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT * FROM cases INNER JOIN users ON cases.fk_customer = users.id WHERE cases.id = ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }
}
