package org.example.reprository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T auto);

    boolean saveAll(List<T> auto);

    void update(T auto);

    boolean deleteById(String id);

    boolean delete(T auto);
}