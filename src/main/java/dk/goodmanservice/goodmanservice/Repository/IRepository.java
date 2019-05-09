package dk.goodmanservice.goodmanservice.Repository;

import java.sql.ResultSet;

public interface IRepository<T> {

    void create(T obj);
    void edit(T obj, String option);
    void delete(int id);
    ResultSet fetch(String option, int id);
    ResultSet findById(int id);

}
