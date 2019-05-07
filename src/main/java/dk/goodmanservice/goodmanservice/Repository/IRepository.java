package dk.goodmanservice.goodmanservice.Repository;

import java.sql.ResultSet;

public interface IRepository<T> {

    void create(T obj);
    void edit(T obj);
    void delete(int id);
    ResultSet fetchAll();
    ResultSet findById(int id);

}
