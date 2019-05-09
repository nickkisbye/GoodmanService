package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Expence;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
@Component("ER")
public class ExpenceRepository implements IRepository<Expence> {

    private Connection con;
    private PreparedStatement preparedStatement;

    public ExpenceRepository() {
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
    public void create(Expence obj) {
        String sql = "INSERT INTO expenses (price, desription, fk_employee) VALUES (?, ?, ?)";
        executeExpense(sql, obj);
    }

    @Override
    public void edit(Expence obj, String option) {
        String sql = "UPDATE expenses SET price=?, description=?, fk_employee=? WHERE id = '" + obj.getId() + "'";
        executeExpense(sql, obj);
    }

    public void executeExpense(String sql, Expence obj) {
        try {
            preparedStatement = con.prepareStatement(sql);;
            preparedStatement.setInt(1, obj.getPrice());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setInt(3, obj.getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM expenses WHERE id=?";
        try {
            preparedStatement = con.prepareStatement(sql);;
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet fetch(String option) {
        String sql = "SELECT * FROM expenses";
        try {
            preparedStatement = con.prepareStatement(sql);;
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        String sql = "SELECT * FROM expenses WHERE id=?";
        try {
            preparedStatement = con.prepareStatement(sql);;
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
