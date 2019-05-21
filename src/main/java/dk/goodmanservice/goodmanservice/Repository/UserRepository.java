package dk.goodmanservice.goodmanservice.Repository;
import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Component("UR")
public class UserRepository implements IRepository<User> {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public UserRepository() throws SQLException {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
    }

    @Override
    public void create(User obj) throws SQLException {
        String sql = "INSERT INTO users " +
                "(firstName, lastName, email, address, phone, fk_role, password, city, zip) " +
                "VALUES (?, ?, ?, ?, ?, ?, md5(?), ?, ?)";
        executeUser(sql, obj);
    }

    @Override
    public void edit(User obj) throws SQLException {
        System.out.print(obj.getRid());
        sql = "UPDATE users " +
                "SET firstName=?, lastName=?, email=?, address=?, " +
                "phone=?, fk_role=?, password=md5(?), city=?, zip=? " +
                "WHERE id = '" + obj.getId() + "'";
        executeUser(sql, obj);
    }

    private void executeUser(String sql, User obj) throws SQLException {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getLastName());
            preparedStatement.setString(3, obj.getEmail());
            preparedStatement.setString(4, obj.getAddress());
            preparedStatement.setString(5, obj.getPhoneNumber());
            preparedStatement.setInt(6, obj.getRid());
            preparedStatement.setString(7, obj.getPassword());
            preparedStatement.setString(8, obj.getCity());
            preparedStatement.setInt(9, obj.getZip());
            preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM users WHERE id=?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    @Override
    public ResultSet fetch(String option) throws SQLException {
        switch (option) {
            case "all":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id";
                break;
            case "employees":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE fk_role=2";
                break;
            case "customers":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE fk_role=1";
                break;
            case "boss":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE fk_role=4";
                break;
            case "manager":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE fk_role=3";
                break;
            case "allEmployees":
                sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE fk_role=2 OR fk_role=3 OR fk_role=4";
            break;
            case "roles":
                sql = "SELECT * FROM roles";
                break;
        }

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT * FROM users WHERE id=?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }

    public ResultSet customerSearch(String search) throws SQLException {
        sql = "SELECT * FROM users INNER JOIN roles ON users.fk_role = roles.id WHERE " +
                "firstName LIKE ? OR email LIKE ? OR address LIKE ? OR phone LIKE ?";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");
        preparedStatement.setString(4, "%" + search + "%");

        return preparedStatement.executeQuery();
    }
}
