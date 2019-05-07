package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Invoice;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("IS")
public class InvoiceService implements IService<Invoice> {

    @Autowired
    private IRepository<Invoice> IR;

    @Override
    public void create(Invoice obj) {

    }

    @Override
    public void edit(Invoice obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Invoice> fetchAll() {
        return null;
    }

    @Override
    public Invoice findById(int id) {
        return null;
    }
}
