package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Offer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
@Component("OR")
public class OfferRepository implements IRepository<Offer> {
    @Override
    public void create(Offer obj) {

    }

    @Override
    public void edit(Offer obj) {

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
