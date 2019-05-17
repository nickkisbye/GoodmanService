package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Component("CS")
public class CaseService implements IService<Case>{

    @Autowired
    private IRepository<Case> CR;
    private ResultSet rs;

    @Override
    public String create(Case obj) throws SQLException {
        CR.create(obj);
        return "Success";
    }

    @Override
    public String edit(Case obj) throws SQLException {
        CR.edit(obj);
        return "";
    }

    @Override
    public void delete(int id) throws SQLException {
        CR.delete(id);
    }

    @Override
    public List<Case> fetch(String option) throws SQLException {

        rs = CR.fetch(option);
        List<Case> cList = new ArrayList<>();

        while (rs.next()) {
            Case c = new Case();
            User user = new User();
            c.setId(rs.getInt("cases.id"));
            c.setStartDate(rs.getString("cases.startDate"));
            c.setEndDate(rs.getString("cases.endDate"));
            c.setDescription(rs.getString("cases.description"));
            c.setPrice(rs.getInt("cases.price"));
            c.setMode(rs.getInt("cases.fk_mode"));
            c.setStartTime(rs.getString("cases.startTime"));
            c.setEndTime(rs.getString("cases.endTime"));
            user.setId(rs.getInt("users.id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setZip(rs.getInt("zip"));
            user.setLevel(rs.getInt("level"));
            c.setCustomer(user);
            cList.add(c);
        }
        return cList;

    }


    @Override
    public Case findById(int id) throws SQLException {
        ResultSet rs = CR.findById(id);
        Case c = new Case();

        while (rs.next()) {
            User user = new User();
            c.setId(rs.getInt("cases.id"));
            c.setStartDate(rs.getString("cases.startDate"));
            c.setEndDate(rs.getString("cases.endDate"));
            c.setDescription(rs.getString("cases.description"));
            c.setPrice(rs.getInt("cases.price"));
            c.setMode(rs.getInt("cases.fk_mode"));
            c.setStartTime(rs.getString("cases.startTime"));
            c.setEndTime(rs.getString("cases.endTime"));
            user.setId(rs.getInt("users.id"));
            user.setFirstName(rs.getString("firstName"));
            user.setPhoneNumber(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setLastName(rs.getString("lastName"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setZip(rs.getInt("zip"));
            c.setCustomer(user);
        }
        return c;
    }
}
