package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

/**
 * Lavet af Nick
 */

@Repository
public class LoginRepository {

    private PreparedStatement preparedStatement;

    @Autowired
    private DBConnect db;

    /**
    Sammenligner email og password med allerede eksiterende brugere i databasen for at tjekke på login oplysninger er korrekte.
     */
    public ResultSet login(User obj) throws SQLException {
        String sql = "SELECT * FROM users " +
                "INNER JOIN roles ON users.fk_role = roles.id " +
                "WHERE email=? AND password=md5(?)";
            preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, obj.getEmail());
            preparedStatement.setString(2, obj.getPassword());

            return preparedStatement.executeQuery();
    }

}
