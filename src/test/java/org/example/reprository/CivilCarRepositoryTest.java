package org.example.reprository;

import org.example.model.CivilCar;
import org.example.model.FuelType;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class CivilCarRepositoryTest {
    private CivilCarRepository target;
    private CivilCar civilCar;

    @BeforeEach
    void setUp() {
        target = new CivilCarRepository();
        civilCar = createSimpleCivilCar();
        target.save(civilCar);
    }

    public CivilCar createSimpleCivilCar() {
        return new CivilCar("Model", BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, 0.0, FuelType.PETROL);
    }

    @Test
    void getById_findOne() {
        final CivilCar actual = target.getById(civilCar.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getId(), civilCar.getId());
    }

    @Test
    void getById_notFind() {
        final CivilCar actual = target.getById("nothing");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_manyCivilCars() {
        final CivilCar otherCivilCar = createSimpleCivilCar();
        Assertions.assertNotNull(otherCivilCar);
        final boolean savedCivilCar = target.save(otherCivilCar);
        Assertions.assertTrue(savedCivilCar);
        final CivilCar actual = target.getById(civilCar.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getId(), civilCar.getId());
        Assertions.assertNotEquals(actual.getId(), otherCivilCar.getId());
    }

    @Test
    void getAll() {
        final List<CivilCar> actual = target.getAll();
        final int expectedOne = 1;
        final int expectedTwo = 2;
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.size(), expectedOne);
        target.saveAll(actual);
        Assertions.assertEquals(actual.size(), expectedTwo);
    }

    @Test
    void getAll_NotEmptyList() {
        final List<CivilCar> expected = new LinkedList<>();
        final List<CivilCar> actual = target.getAll();
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void getAll_EmptyList() {
        target.delete(civilCar);
        final List<CivilCar> expected = new LinkedList<>();
        final List<CivilCar> actual = target.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAll_checkListSize() {
        int expected = 1;
        final int actual = target.getAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void save_success() {
        final boolean actual = target.save(createSimpleCivilCar());
        Assertions.assertTrue(actual);
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        Assertions.assertEquals(BigDecimal.valueOf(-1), civilCar.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        civilCar.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(civilCar);
        Assertions.assertTrue(actual);
        final CivilCar expected = target.getById(civilCar.getId());
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(civilCar.getPrice(), expected.getPrice());
    }

    @Test
    void saveAll() {
        final boolean actual = target.saveAll(List.of(createSimpleCivilCar()));
        Assertions.assertTrue(actual);
    }

    @Test
    void saveAll_null() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.saveAll(null));
    }

    @Test
    void saveAll_emptyList() {
        final boolean actual = target.saveAll(Collections.emptyList());
        Assertions.assertFalse(actual);
    }

    @Test
    void update_notFound() {
        boolean actual = target.update(createSimpleCivilCar());
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        civilCar.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(civilCar);
        Assertions.assertTrue(actual);
        final CivilCar expected = target.getById(civilCar.getId());
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(civilCar.getPrice(), expected.getPrice());
    }

    @Test
    void updateByModel() {
        final CivilCar otherCivilCar = createSimpleCivilCar();
        otherCivilCar.setManufacturer(Manufacturer.AUDI);
        otherCivilCar.setPrice(BigDecimal.TEN);
        final boolean actual = target.updateByModel(civilCar.getModel(), otherCivilCar);
        Assertions.assertTrue(actual);
        final CivilCar expected = target.getById(civilCar.getId());
        Assertions.assertEquals(otherCivilCar.getPrice(), expected.getPrice());
        Assertions.assertEquals(Manufacturer.HYUNDAI, expected.getManufacturer());
    }

    @Test
    void delete() {
        final boolean actual = target.deleteById(civilCar.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final boolean actual = target.deleteById("something");
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteCivilAuto() {
        final boolean actual = target.delete(civilCar);
        Assertions.assertTrue(actual);
    }
}