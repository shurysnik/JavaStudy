package org.example.reprository;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class AutoRepositoryTest {

    private AutoRepository target;
    private Auto auto;

    @BeforeEach
    void setUp() {
        target = new AutoRepository();
        auto = createSimpleAuto();
        target.save(auto);
    }

    public Auto createSimpleAuto() {
        return new Auto("Model", BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Type");
    }

    @Test
    void getById_findOne() {
        final Auto actual = target.getById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Auto actual = target.getById("nothing");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_manyAutos() {
        final Auto otherAuto = createSimpleAuto();
        Assertions.assertNotNull(otherAuto);
        final boolean actual = target.save(otherAuto);
        Assertions.assertTrue(actual);
        final Auto expected = target.getById(auto.getId());
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(auto.getId(), expected.getId());
        Assertions.assertNotNull(auto.getId(), otherAuto.getId());
    }

    @Test
    void getAll() {
        final List<Auto> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getAll_NotEmptyList() {
        List<Auto> expected = new LinkedList<>();
        List<Auto> actual = target.getAll();
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void getAll_checkListSize() {
        int expected = 1;
        final int actual = target.getAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void save_success() {
        final boolean actual = target.save(auto);
        Assertions.assertTrue(actual);
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        Assertions.assertEquals(BigDecimal.valueOf(-1), auto.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        auto.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(auto);
        Assertions.assertTrue(actual);
        final Auto expected = target.getById(auto.getId());
        Assertions.assertEquals(BigDecimal.ONE, expected.getPrice());
    }

    @Test
    void saveAll() {
        final boolean actual = target.saveAll(List.of(createSimpleAuto()));
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
        final Auto otherAuto = createSimpleAuto();
        final boolean actual = target.update(otherAuto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        auto.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(auto);
        Assertions.assertTrue(actual);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertNotNull(actualAuto);
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void updateByBodyType() {
        final Auto otherAuto = createSimpleAuto();
        otherAuto.setManufacturer(Manufacturer.AUDI);
        otherAuto.setPrice(BigDecimal.TEN);
        final boolean actual = target.updateByBodyType(auto.getBodyType(), otherAuto);
        Assertions.assertTrue(actual);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertEquals(Manufacturer.HYUNDAI, actualAuto.getManufacturer());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void delete() {
        final boolean actual = target.delete(auto.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final boolean actual = target.delete("nothing");
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteAuto() {
        final boolean actual = target.delete(auto);
        Assertions.assertTrue(actual);
    }
}
