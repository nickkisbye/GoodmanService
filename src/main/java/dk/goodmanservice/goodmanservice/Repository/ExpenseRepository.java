package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

/**
 * Lavet af Markus
 */

@Repository
@Component("ER")
public class ExpenseRepository implements IRepository<Expense> {

    private PreparedStatement preparedStatement;
    private String sql;

    @Autowired
    private DBConnect db;

    /**
     * ExpenseRepository står for CRUD på Expenses og laver udtræk og indsæt til databasen,
     * opbygningen kommer fra vores Interface.
     */

    @Override
    public void create(Expense obj) throws SQLException {
        sql = "INSERT INTO expenses (price, description, paid, fk_employee) " +
                "VALUES (?, ?, ?, ?)";
        preparedStatement = db.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.setBoolean(3, obj.getPaid());
        preparedStatement.setInt(4, obj.getEmployeeId());
        preparedStatement.execute();
    }

    @Override
    public void edit(Expense obj) throws SQLException {
        sql = "UPDATE expenses " +
                "SET price=?, description=?, paid=? " +
                "WHERE id = '" + obj.getId() + "'";
        preparedStatement = db.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, obj.getPrice());
        preparedStatement.setString(2, obj.getDescription());
        preparedStatement.setBoolean(3, obj.getPaid());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM expenses " +
                "WHERE id = ?";

            preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    /**
     * option er med i metode-deklarationen for at overholde Interfacet, men bliver i realiteten ikke brugt i denne klasse,
     * da der ikke er nødvendighed for andre specifikke udtræk.
     */

    @Override
    public ResultSet fetch(String option) throws SQLException {
        sql = "SELECT expenses.*, users.firstName, users.lastName " +
                "FROM expenses " +
                "INNER JOIN users ON expenses.fk_employee = users.id ";

         if(!option.equals("all")) {
             sql += "WHERE fk_employee = '" + option + "'";
         }

        sql += " ORDER BY expenses.id DESC";

            preparedStatement = db.getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT expenses.* FROM expenses " +
                "WHERE id = ?";

            preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
