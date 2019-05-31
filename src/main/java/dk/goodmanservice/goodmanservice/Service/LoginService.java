package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lavet af Nick
 */

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    /**
     Tjekker for om der er en user i databasen.
     Hvis der er det, sætter den users værdier og returner true.
     */
    public boolean login(User user) throws SQLException {
            ResultSet rs = loginRepository.login(user);
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setPhoneNumber(rs.getString("phone"));
                user.setRoleName(rs.getString("roles.role_name"));
                user.setLevel(rs.getInt("roles.level"));
                return true;
            }
        return false;
    }

}
