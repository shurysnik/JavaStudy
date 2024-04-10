package org.example.reprository;


import org.example.model.Auto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AutoRepository implements CrudRepository<Auto> {
    private static AutoRepository instance;
    private final List<Auto> autos;

    private AutoRepository() {
        autos = new LinkedList<>();
    }

    public static AutoRepository getInstance() {
        if (instance == null) {
            instance = new AutoRepository();
        }
        return instance;
    }

    public Optional<Auto> findById(String id) {
        return autos.stream()
                .filter(auto -> auto.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        autos.add(auto);
        return true;
    }

    @Override
    public boolean saveAll(List<Auto> savedAutos) {
        if (savedAutos == null) {
            throw new IllegalArgumentException("Autos must not be null");
        }
        return autos.addAll(savedAutos);
    }

    @Override
    public void update(Auto auto) {
        Optional<Auto> optionalAuto = findById(auto.getId());
        optionalAuto.ifPresentOrElse(founded -> AutoCopy.copy(auto, founded),
                () -> save(auto));
    }

    @Override
    public boolean deleteById(String id) {
        return autos.removeIf(auto -> auto.getId().equals(id));
    }

    @Override
    public boolean delete(Auto auto) {
        return autos.remove(auto);
    }

    public void resetForTest() {
        autos.clear();
    }

    private static class AutoCopy {
        static void copy(final Auto from, final Auto to) {
            to.setModel(from.getModel());
            to.setRacingTires(from.getRacingTires());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}