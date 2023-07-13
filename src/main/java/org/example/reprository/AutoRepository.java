package org.example.reprository;

import org.example.model.Auto;

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
    public boolean create(Auto auto) {
        return autos.add(auto);

    }

    @Override
    public boolean createAll(List<Auto> auto) {
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

    @Override
    public boolean delete(String id) {
        return autos.removeIf(auto -> auto.getId().equals(id));
    }
    @Override
    public boolean deleteAuto(Auto auto) {
        return autos.remove(auto);
    }

    private static class AutoCopy {
        static void copy(Auto from, Auto to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setManufacturer(from.getManufacturer());
            to.setPrice(from.getPrice());
        }
    }
}