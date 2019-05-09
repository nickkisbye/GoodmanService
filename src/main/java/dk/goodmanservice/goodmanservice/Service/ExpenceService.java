package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Expence;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("ES")
public class ExpenceService implements IService<Expence> {

    @Autowired
    private IRepository<Expence> ER;

    @Override
    public String create(Expence obj) {
        return "";
    }

    @Override
    public String edit(Expence obj, String option) {
        return "";
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Expence> fetch(String option, int id) {
        return null;
    }

    @Override
    public Expence findById(int id) {
        return null;
    }
}
