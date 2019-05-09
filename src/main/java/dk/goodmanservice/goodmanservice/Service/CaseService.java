package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("CS")
public class CaseService implements IService<Case> {

    @Autowired
    private IRepository<Case> CR;

    @Override
    public String create(Case obj) {
        CR.create(obj);
        return "Success";
    }

    @Override
    public String edit(Case obj, String option) {
        return "";
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Case> fetch(String option, int id) {
        return null;
    }

    @Override
    public Case findById(int id) {
        return null;
    }
}
