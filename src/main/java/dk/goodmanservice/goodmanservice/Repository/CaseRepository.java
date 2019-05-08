package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Case;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Component("CR")
public class CaseRepository implements IRepository<Case> {

    private Connection con;
    private PreparedStatement preparedStatement;

    public CaseRepository() {
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
    public void create(Case obj) {

        String sql = "INSERT INTO cases (description, price, creationDate, startDate, endDate, fk_mode, fk_customer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con.prepareStatement(sql);
            preparedStatement.setString(1, obj.getDescription());
            preparedStatement.setInt(2, obj.getPrice());
            preparedStatement.setDate(3, (Date) obj.getCreationDate());
            preparedStatement.setString(4, obj.getStartDate());
            preparedStatement.setString(5, obj.getEndDate());
            preparedStatement.setInt(6, obj.getMode());
            preparedStatement.setInt(7, obj.getCustomerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void edit(Case obj, String option) {

        String sql = "UPDATE cases SET description=?, price=?, startDate=?, endDate=?, fk_mode";
        try {
            con.prepareStatement(sql);
            preparedStatement.setString(1, obj.getDescription());
            preparedStatement.setInt(2, obj.getPrice());
            preparedStatement.setString(3, obj.getStartDate());
            preparedStatement.setString(4, obj.getEndDate());
            preparedStatement.setInt(5, obj.getMode());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM cases WHERE id=?";
        try {
            con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet fetch(String option) {
        String sql = "SELECT * FROM cases";
        try {
            con.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        String sql = "SELECT * FROM cases WHERE id=?";
        try {
            con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
