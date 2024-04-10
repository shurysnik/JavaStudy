package org.example.reprository;

import org.example.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CivilCarRepositoryTest {
    private CivilCarRepository target;
    private CivilCar civilCar;
    private String civilCarId;

    @BeforeEach
    void setUp() {
        target = CivilCarRepository.getInstance();
        civilCar = createSimpleAuto();
        civilCarId = civilCar.getId();
        target.resetForTest();
    }

    public CivilCar createSimpleAuto() {
        return new CivilCar("model", BigDecimal.ZERO, Manufacturer.AUDI, RacingTires.RAIN, 0.0, FuelType.PETROL, 1);
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
        target.save(civilCar);
        Optional<CivilCar> foundedById = target.findById(civilCarId);
        assertTrue(foundedById.isPresent());
        Assertions.assertEquals(foundedById.get(), civilCar);
    }

    @Test
    void findById_notSaved() {
        Optional<CivilCar> foundedById = target.findById(civilCarId);
        Assertions.assertEquals(foundedById, Optional.empty());
    }

    @Test
    void findById_savedNull() {
        Optional<CivilCar> foundedById = target.findById(null);
        assertFalse(foundedById.isPresent());
    }

    @Test
    void getAll_emptyList() {
        List<CivilCar> actual = target.getAll();
        List<CivilCar> expected = new LinkedList<>();
        assertEquals(actual, expected);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAll_notEmptyList() {
        List<CivilCar> actual = target.getAll();
        target.save(civilCar);
        List<CivilCar> expected = new LinkedList<>();
        expected.add(civilCar);
        assertEquals(actual, expected);
        int expectedSize = 1;
        assertFalse(actual.isEmpty());
        assertEquals(actual.size(), expectedSize);
        target.save(civilCar);
        expectedSize = 2;
        assertEquals(actual.size(), expectedSize);
    }

    @Test
    void save_null() {
        String message = "Civil auto must not be null";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null), message);
    }

    @Test
    void save_checkSize() {
        civilCar.setPrice(BigDecimal.ZERO);
        boolean save = target.save(civilCar);
        assertTrue(save);
        BigDecimal expectedPrice = BigDecimal.valueOf(-1);
        BigDecimal actual = civilCar.getPrice();
        assertEquals(actual, expectedPrice);
        assertTrue(target.getAll().contains(civilCar));
    }

    @Test
    void saveAll_null() {
        String message = "Civil autos must not be null";
        assertThrows(IllegalArgumentException.class, () -> target.saveAll(null), message);
        int actual = 0;
        assertEquals(target.getAll().size(), actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void saveAll() {
        int expectedSize = 1;
        boolean actual = target.saveAll(List.of(civilCar));
        assertTrue(actual);
        assertEquals(target.getAll().size(), expectedSize);
    }

    @Test
    void update() {
        CivilCar updatedAuto = new CivilCar("newModel", BigDecimal.ONE, Manufacturer.MAZDA,
                RacingTires.RAIN, 5.5, FuelType.DIESEL, 2);
        target.save(updatedAuto);
        target.update(updatedAuto);
        Optional<CivilCar> foundedAuto = target.findById(updatedAuto.getId());
        assertTrue(foundedAuto.isPresent());
        String newModel = "newModel";
        BigDecimal newPrice = BigDecimal.ONE;
        Manufacturer newManufacturer = Manufacturer.MAZDA;
        RacingTires newRacingTires = RacingTires.RAIN;
        double newFuelConsumption = 5.5;
        FuelType newFuelType = FuelType.DIESEL;
        int newCount = 2;
        assertEquals(newModel, foundedAuto.get().getModel());
        assertEquals(newPrice, foundedAuto.get().getPrice());
        assertEquals(newManufacturer, foundedAuto.get().getManufacturer());
        assertEquals(newFuelConsumption, foundedAuto.get().getFuelConsumption());
        assertEquals(newFuelType, foundedAuto.get().getFuelType());
        assertEquals(newRacingTires, foundedAuto.get().getRacingTires());
        assertEquals(newCount, foundedAuto.get().getCount());
    }

    @Test
    void deleteById_null() {
        target.save(civilCar);
        int expectedSize = 1;
        boolean actual = target.deleteById(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void deleteById() {
        target.save(civilCar);
        boolean actual = target.deleteById(civilCarId);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void delete_null() {
        target.save(civilCar);
        int expectedSize = 1;
        boolean actual = target.delete(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void delete() {
        target.save(civilCar);
        boolean actual = target.delete(civilCar);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }

}