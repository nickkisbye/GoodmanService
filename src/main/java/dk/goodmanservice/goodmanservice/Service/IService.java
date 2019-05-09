package dk.goodmanservice.goodmanservice.Service;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    String create(T obj) throws SQLException;
    String edit(T obj) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> fetch(String option, int id) throws SQLException;
    T findById(int id) throws SQLException;

}
