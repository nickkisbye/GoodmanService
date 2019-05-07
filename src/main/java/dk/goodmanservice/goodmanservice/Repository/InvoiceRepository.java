package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Invoice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
@Component("IR")
public class InvoiceRepository implements IRepository<Invoice> {

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
    public ResultSet fetchAll() {
        return null;
    }

    @Override
    public ResultSet findById(int id) {
        return null;
    }
}
