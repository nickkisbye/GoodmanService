package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Component("CS")
public class CaseService implements IService<Case> {

    @Autowired
    private IRepository<Case> CR;

    @Override
    public String create(Case obj) throws SQLException {
        CR.create(obj);
        return "Success";
    }

    @Override
    public String edit(Case obj) throws SQLException {
        return "";
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Case> fetch(String option, int id) throws SQLException {
        return null;
    }

    @Override
    public Case findById(int id) throws SQLException {
        return null;
    }
}
