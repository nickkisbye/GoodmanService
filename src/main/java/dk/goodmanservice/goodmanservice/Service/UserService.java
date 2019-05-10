package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import dk.goodmanservice.goodmanservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Component("US")
public class UserService implements IService<User> {

    @Autowired
    private IRepository<User> UR;

    @Autowired
    private Validation validate;
    
    private ResultSet resultSet;

    @Override
    public String create(User obj) throws SQLException {
        if (obj.getFirstName().length() < 2 || obj.getLastName().length() < 2) {
            return "Fornavn eller efternavn må ikke være under 2 karaktere";
        } else {
            UR.create(obj);
            return "success";
        }
    }

    @Override
    public String edit(User obj) throws SQLException {
        if (obj.getFirstName().length() < 2 || obj.getLastName().length() < 2) {
            return "Fornavn eller efternavn må ikke være under 2 karaktere";
        }
         else {
            UR.edit(obj);
            return "success";
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        UR.delete(id);
    }

    @Override
    public List<User> fetch(String option) throws SQLException {
        resultSet = UR.fetch(option);
        List<User> userList = new ArrayList<>();

        if (option.equals("roles")) {
            while (resultSet.next()) {
                User user = new User();
                user.setRole(resultSet.getInt("roles.id"));
                user.setRoleName(resultSet.getString("roles.role_name"));
                userList.add(user);
            }
        } else {
            while (resultSet.next()) {
                User user = new User();
                user.setPhoneNumber(resultSet.getString("phone"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setRoleName(resultSet.getString("roles.role_name"));
                user.setId(resultSet.getInt("id"));
                userList.add(user);
            }
        }
            return userList;
    }

    @Override
    public User findById(int id) throws SQLException {
        User user = new User();
        resultSet = UR.findById(id);

            if (resultSet.next()) {
                user.setPhoneNumber(resultSet.getString("phone"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setRole(resultSet.getInt("fk_role"));
                user.setId(resultSet.getInt("id"));
            }
            return user;
    }
}
