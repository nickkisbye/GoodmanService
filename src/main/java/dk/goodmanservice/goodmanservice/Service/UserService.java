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

    @Override
    public String create(User obj) {
        if (obj.getFirstName().length() < 2 || obj.getLastName().length() < 2) {
            return "Fornavn eller efternavn må ikke være under 2 karaktere";
        } else if(validate.isNumeric(obj.getPhoneNumber())) {
            return "Telefonnummeret skal være tal";
        } else {
            UR.create(obj);
            return "success";
        }
    }

    @Override
    public String edit(User obj, String option) {
        if (obj.getFirstName().length() < 2 || obj.getLastName().length() < 2) {
            return "Fornavn eller efternavn må ikke være under 2 karaktere";
        } else if(validate.isNumeric(obj.getPhoneNumber())) {
            return "Telefonnummeret skal være tal";
        } else {
            UR.edit(obj, option);
            return "success";
        }
    }

    @Override
    public void delete(int id) {
        UR.delete(id);
    }

    @Override
    public List<User> fetch(String option) {
        ResultSet rs = UR.fetch(option);
        List<User> userList = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setPhoneNumber(rs.getString("phone"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getInt("fk_role"));
                user.setId(rs.getInt("id"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int id) {
        User user = new User();
        ResultSet rs = UR.findById(id);
        try {
            user.setPhoneNumber(rs.getString("phone"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getInt("fk_role"));
            user.setId(rs.getInt("id"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
