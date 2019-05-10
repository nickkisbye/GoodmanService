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
    public void create(Expence obj) throws SQLException {
        String sql = "INSERT INTO expenses (price, desription, fk_employee) VALUES (?, ?, ?)";
        preparedStatement = con.prepareStatement(sql);
        executeExpense(obj);
    }

    @Override
    public void edit(Expence obj) throws SQLException {
        String sql = "UPDATE expenses SET price=?, description=?, fk_employee=? WHERE id = '" + obj.getId() + "'";
        preparedStatement = con.prepareStatement(sql);
        executeExpense(obj);
    }

    public void executeExpense(Expence obj) throws SQLException {
            preparedStatement.setInt(1, obj.getPrice());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setInt(3, obj.getEmployeeId());
            preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id=?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {
        String sql = "SELECT * FROM expenses";

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        String sql = "SELECT * FROM expenses WHERE id=?";

            preparedStatement = con.prepareStatement(sql);;
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
