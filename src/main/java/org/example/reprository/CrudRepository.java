package org.example.reprository;


import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T auto);

    boolean saveAll(List<T> auto);

    boolean update(T auto);

    boolean deleteById(String id);

    boolean delete(T auto);
}