package dk.goodmanservice.goodmanservice.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Lavet af Markus, Joachim, Nick
 */

public interface IService<T> {

    String create(T obj) throws SQLException;
    String edit(T obj) throws SQLException;
    String delete(int id) throws SQLException;
    List<T> fetch(String option) throws SQLException;
    T findById(int id) throws SQLException;

}
