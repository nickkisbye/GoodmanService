package dk.goodmanservice.goodmanservice.Service;

import java.util.List;

public interface IService<T> {

    void create(T obj);
    void edit(T obj);
    void delete(int id);
    List<T> fetchAll();
    T findById(int id);

}
