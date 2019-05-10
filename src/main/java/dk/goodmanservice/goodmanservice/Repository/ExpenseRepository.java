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

    public ExpenseRepository() {
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
    public void create(Expense obj) throws SQLException {
        String sql = "INSERT INTO expenses (price, description, fk_employee) VALUES (?, ?, ?)";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.setInt(3, obj.getEmployeeId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Expense obj) throws SQLException {
        String sql = "UPDATE expenses SET price=?, description=? WHERE id = '" + obj.getId() + "'";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {
        String sql = "SELECT expenses.*, users.firstName, users.lastName " +
                "FROM expenses " +
                "INNER JOIN users ON expenses.fk_employee = users.id " +
                "ORDER BY expenses.price";

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        String sql = "SELECT * FROM expenses WHERE id = ?";

            preparedStatement = con.prepareStatement(sql);;
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
