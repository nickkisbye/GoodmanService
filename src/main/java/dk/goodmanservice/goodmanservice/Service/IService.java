package dk.goodmanservice.goodmanservice.Service;

import java.util.List;

public interface IService<T> {

    String create(T obj);
    String edit(T obj, String option);
    void delete(int id);
    List<T> fetch(String option);
    T findById(int id);

}
