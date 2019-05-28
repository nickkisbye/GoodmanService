package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.CaseRepository;
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

    @Autowired
    private Validation V;

    private ResultSet rs;

    /**
     CaseService sørger for at tjekke på alle de værdier der kommer ind og udgiver en fejl, hvis der er skrevet noget forkert.
     CaseService henter den også data og indsætter det i et model objekt, som vi senere kan kalde på i vores html.
     */

    @Override
    public String create(Case obj) throws SQLException {
        String checkSum = V.validateCase(obj);
        if(checkSum.equals("1")) {
            CR.create(obj);
            return "OPGAVEN ER BLEVET OPRETTET";
        }
        return checkSum;
    }

    @Override
    public String edit(Case obj) throws SQLException {
        String checkSum = V.validateCase(obj);
        if(checkSum.equals("1")) {
            CR.edit(obj);
            return "OPGAVEN ER BLEVET REDIGERET";
        }
        return checkSum;
    }

    @Override
    public String delete(int id) throws SQLException {
        CR.delete(id);
        return "OPGAVEN ER BLEVET SLETTET";
    }

    @Override
    public List<Case> fetch(String option) throws SQLException {
        rs = CR.fetch(option);
        return caseSetup();
    }

    public List<Case> fetchById(int id, String option) throws SQLException {
        rs = ((CaseRepository)CR).fetchById(id, option);
        return caseSetup();
    }

    @Override
    public Case findById(int id) throws SQLException {
        rs = CR.findById(id);
        Case c = null;

        while (rs.next()) {
            c = caseFiller();
            User user = userFiller();

            user.setPhoneNumber(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            c.setCustomer(user);
        }
        return c;
    }

    /**
     * 3 Metoder under bruges til at reducere redundans igennem al opsætning.
     */

    private Case caseFiller() throws SQLException {
        Case c = new Case();
        c.setId(rs.getInt("cases.id"));
        c.setStartDate(rs.getString("cases.startDate"));
        c.setEndDate(rs.getString("cases.endDate"));
        c.setDescription(rs.getString("cases.description"));
        c.setPrice(rs.getInt("cases.price"));
        c.setMode(rs.getInt("cases.fk_mode"));
        c.setStartTime(rs.getString("cases.startTime"));
        c.setEndTime(rs.getString("cases.endTime"));

        return c;
    }

    private User userFiller() throws SQLException {
        User user = new User();
        user.setId(rs.getInt("users.id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setAddress(rs.getString("address"));
        user.setCity(rs.getString("city"));
        user.setZip(rs.getInt("zip"));

        return user;
    }

    private List<Case> caseSetup() throws SQLException {
        List<Case> cList = new ArrayList<>();

        while (rs.next()) {
            Case c = caseFiller();
            User user = userFiller();

            user.setLevel(rs.getInt("level"));
            c.setCustomer(user);

            cList.add(c);
        }
        return cList;
    }
}