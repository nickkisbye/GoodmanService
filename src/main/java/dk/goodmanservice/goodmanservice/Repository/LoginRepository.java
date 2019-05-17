package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LoginRepository {

    private Connection con;
    private PreparedStatement preparedStatement;

    public LoginRepository() throws SQLException {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
    }

    public ResultSet login(User obj) throws SQLException {
        String sql = "SELECT * FROM users INNER JOIN roles " +
                "ON users.fk_role = roles.id WHERE email=? AND password=md5(?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, obj.getEmail());
            preparedStatement.setString(2, obj.getPassword());

            return preparedStatement.executeQuery();
    }

}
