package org.example.reprository;

import org.example.model.Auto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Auto getById(String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return auto;
            }
        }
        return null;
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
        return autos.add(auto);

    }

    @Override
    public boolean saveAll(List<Auto> auto) {
        if (auto == null) {
            return false;
        }
        return autos.addAll(auto);
    }

    @Override
    public boolean update(Auto auto) {
        final Auto founded = getById(auto.getId());
        if (founded != null) {
            AutoCopy.copy(auto, founded);
            return true;
        }
        return false;
    }

    public boolean updateByBodyType(String bodyType, Auto copyFrom) {
        for (Auto auto : autos) {
            if (auto.getBodyType().equals(bodyType)) {
                AutoCopy.copy(copyFrom, auto);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        return autos.removeIf(auto -> auto.getId().equals(id));
    }

    @Override
    public boolean delete(Auto auto) {
        return autos.remove(auto);
    }

    private static class AutoCopy {
        static void copy(Auto from, Auto to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}