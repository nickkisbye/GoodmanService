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

    public UserRepository() {
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
    public void create(User obj) {

    }

    @Override
    public void edit(User obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ResultSet fetchAll() {
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        return null;
    }
}
