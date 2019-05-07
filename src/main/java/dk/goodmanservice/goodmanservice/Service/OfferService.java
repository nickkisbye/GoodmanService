package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Offer;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import dk.goodmanservice.goodmanservice.Repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("OS")
public class OfferService implements IService<Offer> {

    @Autowired
    private IRepository<Offer> OR;

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
    public List<Offer> fetchAll() {
        return null;
    }

    @Override
    public Offer findById(int id) {
        return null;
    }
}
