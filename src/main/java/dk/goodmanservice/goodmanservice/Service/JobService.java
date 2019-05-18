package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.Jobs;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import dk.goodmanservice.goodmanservice.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository JR;
    private ResultSet rs;

    public List<Jobs> findByIdJobs(int id) throws SQLException {
        rs = JR.findByIdJobs(id);
        List<Jobs> jList = new ArrayList<>();

        while (rs.next()) {
            Jobs j = new Jobs();
            User u = new User();
            j.setEmployeeId(rs.getInt("fk_employee"));
            j.setCaseId(rs.getInt("fk_case"));
            j.setId(rs.getInt("junc_jobs.id"));
            u.setId(rs.getInt("users.id"));
            u.setFirstName(rs.getString("users.firstName"));
            u.setLastName(rs.getString("users.lastName"));
            j.setEmployees(u);
            jList.add(j);

        }
        return jList;
    }

    public String createJob(Jobs obj) throws SQLException {
        JR.createJob(obj);
        return "success";
    }
    public String deleteJob(int id) throws SQLException {
        JR.deleteJob(id);
        return "success";
    }
    public List<User> fetchEmployees(int id)throws SQLException {
        rs = JR.fetchEmployees(id);
        List<User> uList = new ArrayList<>();

        while(rs.next()) {
            User u = new User();
            u.setId(rs.getInt("users.id"));
            u.setFirstName(rs.getString("users.firstName"));
            u.setLastName(rs.getString("users.lastName"));
            uList.add(u);
        }
        return uList;

    }

    public List<Case> customerOffers(int id) throws SQLException {
        rs = JR.customerOffers(id);
        List<Case> caseList = new ArrayList<>();
        while (rs.next()) {
            Case c = new Case();
            c.setId(rs.getInt("id"));
            c.setDescription(rs.getString("description"));
            c.setPrice(rs.getInt("price"));
            c.setStartDate(rs.getString("startDate"));
            caseList.add(c);
        }
        return caseList;
    }
}
