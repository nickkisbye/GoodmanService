package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Expense;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
@Component("ER")
public class ExpenseRepository implements IRepository<Expense> {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public ExpenseRepository() throws SQLException {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
    }

    @Override
    public void create(Expense obj) throws SQLException {
        sql = "INSERT INTO expenses (price, description, paid, fk_employee) " +
                "VALUES (?, ?, ?, ?)";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.setBoolean(3, obj.getPaid());
        preparedStatement.setInt(4, obj.getEmployeeId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Expense obj) throws SQLException {
        sql = "UPDATE expenses " +
                "SET price=?, description=?, paid=? " +
                "WHERE id = '" + obj.getId() + "'";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.setBoolean(3, obj.getPaid());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM expenses " +
                "WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {
        sql = "SELECT expenses.*, users.firstName, users.lastName " +
                "FROM expenses " +
                "INNER JOIN users ON expenses.fk_employee = users.id " +
                "ORDER BY expenses.price";

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT expenses.* FROM expenses " +
                "WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
