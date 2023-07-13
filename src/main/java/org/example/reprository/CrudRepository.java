package org.example.reprository;

import org.example.model.Auto;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean create(T auto);

    boolean createAll(List<T> auto);

    boolean update(T auto);

    boolean delete(String id);

    boolean deleteAuto(T auto);
}