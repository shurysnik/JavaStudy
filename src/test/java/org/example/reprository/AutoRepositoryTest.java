package org.example.reprository;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AutoRepositoryTest {
    private AutoRepository target;
    private Auto auto;
    private String autoId;

    @BeforeEach
    void setUp() {
        target = AutoRepository.getInstance();
        auto = createSimpleAuto();
        autoId = auto.getId();
        target.resetForTest();
    }

    public Auto createSimpleAuto() {
        return new Auto("model-1", BigDecimal.ZERO, Manufacturer.TOYOTA,
                RacingTires.RACING, "body type ", 1);
    }

    @Test
    void getInstance() {
        AutoRepository firstInstance = AutoRepository.getInstance();
        Assertions.assertNotNull(firstInstance);
        AutoRepository secondInstance = AutoRepository.getInstance();
        Assertions.assertSame(firstInstance, secondInstance);
        Assertions.assertEquals(firstInstance, secondInstance);
    }

    @Test
    void findById_saved() {
        target.save(auto);
        Optional<Auto> foundedById = target.findById(autoId);
        assertTrue(foundedById.isPresent());
        Assertions.assertEquals(foundedById.get(), auto);
    }

    @Test
    void findById_notSaved() {
        Optional<Auto> foundedById = target.findById(autoId);
        Assertions.assertEquals(foundedById, Optional.empty());
    }

    @Test
    void findById_savedNull() {
        Optional<Auto> foundedById = target.findById(null);
        assertFalse(foundedById.isPresent());
    }

    @Test
    void getAll_emptyList() {
        List<Auto> actual = target.getAll();
        List<Auto> expected = new LinkedList<>();
        assertEquals(actual, expected);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAll_notEmptyList() {
        List<Auto> actual = target.getAll();
        target.save(auto);
        List<Auto> expected = new LinkedList<>();
        expected.add(auto);
        assertEquals(actual, expected);
        int expectedSize = 1;
        assertFalse(actual.isEmpty());
        assertEquals(actual.size(), expectedSize);
        target.save(auto);
        expectedSize = 2;
        assertEquals(actual.size(), expectedSize);
    }

    @Test
    void save_null() {
        String message = "Auto must not be null";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null), message);
    }

    @Test
    void save_checkSize() {
        auto.setPrice(BigDecimal.ZERO);
        boolean save = target.save(auto);
        assertTrue(save);
        BigDecimal expectedPrice = BigDecimal.valueOf(-1);
        BigDecimal actual = auto.getPrice();
        assertEquals(actual, expectedPrice);
        assertTrue(target.getAll().contains(auto));
    }

    @Test
    void saveAll_null() {
        String message = "Autos must not be null";
        assertThrows(IllegalArgumentException.class, () -> target.saveAll(null), message);
        int actual = 0;
        assertEquals(target.getAll().size(), actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void saveAll() {
        int expectedSize = 1;
        boolean actual = target.saveAll(List.of(auto));
        assertTrue(actual);
        assertEquals(target.getAll().size(), expectedSize);
    }

    @Test
    void update() {
        Auto updatedAuto = new Auto("newModel", BigDecimal.ONE,
                Manufacturer.AUDI, RacingTires.RAIN, "new bodyType", 2);
        target.save(updatedAuto);
         target.update(updatedAuto);
         Optional<Auto> foundedAuto = target.findById(updatedAuto.getId());
        assertTrue(foundedAuto.isPresent());
        String newModel = "newModel";
        BigDecimal newPrice = BigDecimal.ONE;
        Manufacturer newManufacturer = Manufacturer.AUDI;
        RacingTires newRacingTires = RacingTires.RAIN;
        String newBodyType = "new bodyType";
        int newCount = 2;
        assertEquals(newModel, foundedAuto.get().getModel());
        assertEquals(newPrice, foundedAuto.get().getPrice());
        assertEquals(newManufacturer, foundedAuto.get().getManufacturer());
        assertEquals(newBodyType, foundedAuto.get().getBodyType());
        assertEquals(newRacingTires, foundedAuto.get().getRacingTires());
        assertEquals(newCount, foundedAuto.get().getCount());
    }

    @Test
    void deleteById_null() {
        target.save(auto);
        int expectedSize = 1;
        boolean actual = target.deleteById(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void deleteById() {
        target.save(auto);
        boolean actual = target.deleteById(autoId);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void delete_null() {
        target.save(auto);
        int expectedSize = 1;
        boolean actual = target.delete(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void delete() {
        target.save(auto);
        boolean actual = target.delete(auto);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }

}