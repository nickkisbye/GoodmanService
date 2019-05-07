package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Case;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
@Component("CR")
public class CaseRepository implements IRepository<Case> {
    @Override
    public void create(Case obj) {

    }

    @Override
    public void edit(Case obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ResultSet fetchAll() {
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        return null;
    }
}
