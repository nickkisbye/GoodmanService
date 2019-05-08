package dk.goodmanservice.goodmanservice.Service;

import java.util.List;

public interface IService<T> {

    void create(T obj);
    void edit(T obj, String option);
    void delete(int id);
    List<T> fetch(String option);
    T findById(int id);

}
