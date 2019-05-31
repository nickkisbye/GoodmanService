package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Expense;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lavet af Markus
 */

@Service
@Component("ES")
public class ExpenseService implements IService<Expense> {

    @Autowired
    private IRepository<Expense> ER;

    @Autowired
    private Validation V;

    private ResultSet resultSet;

    /**
     * ExpenseService benytter sig af vores CRUD Interface og sørger udelukkende for inmplementeringen af disse,
     * dette betyder at det er en meget standard Serviceklasse som bare sørger for opsætning fra og videregivelse til -Repo.
     * Validation er også med til at sørge for at de værdier der kommer hertil bliver ordentligt tjekket inden de når Repo og Databasen.
     */

    @Override
    public String create(Expense obj) throws SQLException {
        String checkSum = V.validateExpense(obj);
        if(checkSum.equals("1")) {
            ER.create(obj);
            return "OPRETTET";
        }
        return checkSum;
    }

    @Override
    public String edit(Expense obj) throws SQLException {
        String checkSum = V.validateExpense(obj);
        if(checkSum.equals("1")) {
            ER.edit(obj);
            return "REDIGERET";
        }
        return checkSum;
    }

    @Override
    public String delete(int id) throws SQLException {
        ER.delete(id);
        return "SLETTET";
    }

    @Override
    public List<Expense> fetch(String option) throws SQLException {
        resultSet = ER.fetch(option);
        List<Expense> expenseList = new ArrayList<>();

        while (resultSet.next()) {
            Expense expense = expenseFiller();
            User employee = new User();

            employee.setFirstName(resultSet.getString("firstName"));
            employee.setLastName(resultSet.getString("lastName"));

            expense.setEmployee(employee);

            expenseList.add(expense);
        }
        return expenseList;
    }

    @Override
    public Expense findById(int id) throws SQLException {
        resultSet = ER.findById(id);
        Expense expense = null;

        while (resultSet.next()) {
            expense = expenseFiller();
        }
        return expense;
    }

    /**
     * Brugt til at minimere redundans på opsætning af Expense objekter.
     */

    private Expense expenseFiller() throws SQLException {
        Expense expense = new Expense();
        expense.setId(resultSet.getInt("id"));
        expense.setPrice(resultSet.getInt("price"));
        expense.setDescription(resultSet.getString("description"));
        expense.setPaid(resultSet.getBoolean("paid"));
        expense.setEmployeeId(resultSet.getInt("fk_employee"));

        return expense;
    }
}
