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

@Service
@Component("ES")
public class ExpenseService implements IService<Expense> {

    @Autowired
    private IRepository<Expense> ER;

    private ResultSet resultSet;

    @Override
    public String create(Expense obj) throws SQLException {
        String check = checker(obj);
        if(check.equals("success")) {
            ER.create(obj);
        }
        return check;
    }

    private String checker(Expense obj) {
        if(obj.getPrice() == null || obj.getPrice() < 1) {
            return "Invalid Price";
        } else if(obj.getDescription() == null || obj.getDescription().length() < 1) {
            return "Invalid Description";
        }
        return "success";
    }

    @Override
    public String edit(Expense obj) throws SQLException {
        ER.edit(obj);
        return "success";
    }

    @Override
    public void delete(int id) throws SQLException {
        ER.delete(id);
    }

    @Override
    public List<Expense> fetch(String option) throws SQLException {
        resultSet = ER.fetch(option);
        List<Expense> expenseList = new ArrayList<>();

        while (resultSet.next()) {
            Expense expense = new Expense();
            User employee = new User();

            expense.setId(resultSet.getInt("id"));
            expense.setPrice(resultSet.getInt("price"));
            expense.setDescription(resultSet.getString("description"));
            expense.setPaid(resultSet.getBoolean("paid"));
            expense.setEmployeeId(resultSet.getInt("fk_employee"));

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
        Expense expense = new Expense();

        while (resultSet.next()) {
            expense.setId(resultSet.getInt("id"));
            expense.setPrice(resultSet.getInt("price"));
            expense.setDescription(resultSet.getString("description"));
            expense.setPaid(resultSet.getBoolean("paid"));
            expense.setEmployeeId(resultSet.getInt("fk_employee"));
        }
        return expense;
    }
}
