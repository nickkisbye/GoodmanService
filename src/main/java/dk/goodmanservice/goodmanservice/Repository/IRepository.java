package dk.goodmanservice.goodmanservice.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository<T> {

    void create(T obj) throws SQLException;
    void edit(T obj) throws SQLException;
    void delete(int id) throws SQLException;
    ResultSet fetch(String option, int id) throws SQLException;
    ResultSet findById(int id) throws SQLException;

}
