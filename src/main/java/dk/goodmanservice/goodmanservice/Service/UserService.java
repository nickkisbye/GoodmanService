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
    private Validation V;
    
    private ResultSet resultSet;

    /**
     UserService sørger for forretningslogikken i de kald der sker, når en bruger bliver oprettet eller rettet i.
     UserService henter også data ud fra "fetch" metoden. Indholdet af kaldet varierer alt efter hvilken "option" der er
     blevet parset igennem.
     */

    @Override
    public String create(User obj) throws SQLException {
        String checkSum = V.validateUser(obj);
        if(checkSum.equals("1")) {
            UR.create(obj);
            return "OPRETTET";
        }
        return checkSum;
    }

    @Override
    public String edit(User obj) throws SQLException {
        String checkSum = V.validateUser(obj);
        if(checkSum.equals("1")) {
            UR.edit(obj);
            return "REDIGERET";
        }
        return checkSum;
    }

    @Override
    public String delete(int id) throws SQLException {
        UR.delete(id);
        return "SLETTET";
    }

    /**
     "option" bliver parset igennem fra Controlleren og ned til Repository laget.
     */

    @Override
    public List<User> fetch(String option) throws SQLException {
        resultSet = UR.fetch(option);
        List<User> userList = new ArrayList<>();

        if (option.equals("roles")) {
            while (resultSet.next()) {
                User user = new User();
                user.setRid(resultSet.getInt("roles.id"));
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
                user.setRid(resultSet.getInt("fk_role"));
                user.setRoleName(resultSet.getString("roles.role_name"));
                user.setLevel(resultSet.getInt("level"));
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
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setRid(resultSet.getInt("fk_role"));
                user.setId(resultSet.getInt("id"));
                user.setCity(resultSet.getString("city"));
                user.setZip(resultSet.getInt("zip"));
            }
            return user;
    }

    /**
     Da customerSearch ikke hører til UserRepository interfacet, har vi måtte caste interfacet, så den stadig kan
     finde ud af det.
     */

    public List<User> customerSearch(String search) throws SQLException {
        resultSet = ((UserRepository)UR).customerSearch(search);
        List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                User users = new User();
                users.setPhoneNumber(resultSet.getString("phone"));
                users.setFirstName(resultSet.getString("firstName"));
                users.setLastName(resultSet.getString("lastName"));
                users.setEmail(resultSet.getString("email"));
                users.setAddress(resultSet.getString("address"));
                users.setRid(resultSet.getInt("fk_role"));
                users.setRoleName(resultSet.getString("roles.role_name"));
                users.setLevel(resultSet.getInt("level"));
                users.setId(resultSet.getInt("id"));
                userList.add(users);
            }

        return userList;
    }
}
