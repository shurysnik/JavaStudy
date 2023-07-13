package org.example.reprository;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean create(T auto);

    boolean create(List<T> auto);

    boolean update(T auto);

    boolean delete(String id);

}