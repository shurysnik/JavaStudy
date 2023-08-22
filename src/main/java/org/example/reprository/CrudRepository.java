package org.example.reprository;


import org.example.model.Auto;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T getById(String id);

    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T auto);

    boolean saveAll(List<T> auto);

    boolean update(T auto);

    boolean deleteById(String id);

    boolean delete(T auto);
}