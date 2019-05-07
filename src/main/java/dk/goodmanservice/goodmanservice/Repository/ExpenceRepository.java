package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Expence;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;


@Repository
@Component("ER")
public class ExpenceRepository implements IRepository<Expence> {
    @Override
    public void create(Expence obj) {

    }

    @Override
    public void edit(Expence obj) {

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
