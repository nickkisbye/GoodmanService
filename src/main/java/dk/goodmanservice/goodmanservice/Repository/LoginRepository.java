package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LoginRepository {

    private Connection con;
    private PreparedStatement preparedStatement;

    public LoginRepository() {
        try {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet login(User obj) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, obj.getEmail());
            preparedStatement.setString(2, obj.getPassword());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
