package dk.goodmanservice.goodmanservice.Repository;

import java.util.List;

public interface Repository<T> {

    void create(T obj);
    void edit(T obj);
    void delete(int id);
    List<T> fetchAll();
    T findById(int id);

}
