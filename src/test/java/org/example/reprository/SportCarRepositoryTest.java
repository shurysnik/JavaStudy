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

class SportCarRepositoryTest {

    private SportCarRepository target;
    private SportCar sportCar;
    private String sportCarId;

    @BeforeEach
    void setUp() {
        target = SportCarRepository.getInstance();
        sportCar = createSimpleAuto();
        sportCarId = sportCar.getId();
        target.resetForTest();
    }

    public SportCar createSimpleAuto() {
        return new SportCar("model", BigDecimal.ZERO, Manufacturer.AUDI,
                RacingTires.RAIN, BigDecimal.ZERO, 2000, Color.BLACK, 1);
    }

    @Test
    void getInstance() {
        SportCarRepository firstInstance = SportCarRepository.getInstance();
        Assertions.assertNotNull(firstInstance);
        SportCarRepository secondInstance = SportCarRepository.getInstance();
        Assertions.assertSame(firstInstance, secondInstance);
        Assertions.assertEquals(firstInstance, secondInstance);
    }

    @Test
    void findById_saved() {
        target.save(sportCar);
        Optional<SportCar> foundedById = target.findById(sportCarId);
        assertTrue(foundedById.isPresent());
        Assertions.assertEquals(foundedById.get(), sportCar);
    }

    @Test
    void findById_notSaved() {
        Optional<SportCar> foundedById = target.findById(sportCarId);
        Assertions.assertEquals(foundedById, Optional.empty());
    }

    @Test
    void findById_savedNull() {
        Optional<SportCar> foundedById = target.findById(null);
        assertFalse(foundedById.isPresent());
    }

    @Test
    void getAll_emptyList() {
        List<SportCar> actual = target.getAll();
        List<SportCar> expected = new LinkedList<>();
        assertEquals(actual, expected);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAll_notEmptyList() {
        List<SportCar> actual = target.getAll();
        target.save(sportCar);
        List<SportCar> expected = new LinkedList<>();
        expected.add(sportCar);
        assertEquals(actual, expected);
        int expectedSize = 1;
        assertFalse(actual.isEmpty());
        assertEquals(actual.size(), expectedSize);
        target.save(sportCar);
        expectedSize = 2;
        assertEquals(actual.size(), expectedSize);
    }

    @Test
    void save_null() {
        String message = "Sport car must not be null";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null), message);
    }

    @Test
    void save_checkSize() {
        sportCar.setPrice(BigDecimal.ZERO);
        boolean save = target.save(sportCar);
        assertTrue(save);
        BigDecimal expectedPrice = BigDecimal.valueOf(-1);
        BigDecimal actual = sportCar.getPrice();
        assertEquals(actual, expectedPrice);
        assertTrue(target.getAll().contains(sportCar));
    }

    @Test
    void saveAll_null() {
        String message = "Sport car must not be null";
        assertThrows(IllegalArgumentException.class, () -> target.saveAll(null), message);
        int actual = 0;
        assertEquals(target.getAll().size(), actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void saveAll() {
        int expectedSize = 1;
        boolean actual = target.saveAll(List.of(sportCar));
        assertTrue(actual);
        assertEquals(target.getAll().size(), expectedSize);
    }

    @Test
    void update() {
        SportCar updatedSportCar = new SportCar("newModel", BigDecimal.ONE,
                Manufacturer.MAZDA, RacingTires.ROAD, BigDecimal.valueOf(240),
                2000, Color.YELLOW, 2);
        target.save(updatedSportCar);
        target.update(updatedSportCar);
        Optional<SportCar> foundedAuto = target.findById(updatedSportCar.getId());
        assertTrue(foundedAuto.isPresent());
        String newModel = "newModel";
        BigDecimal newPrice = BigDecimal.ONE;
        Manufacturer newManufacturer = Manufacturer.MAZDA;
        RacingTires newRacingTires = RacingTires.ROAD;
        BigDecimal newSpeed = BigDecimal.valueOf(240);
        int newYear = 2000;
        Color newColor = Color.YELLOW;
        int newCount = 2;
        assertEquals(newModel, foundedAuto.get().getModel());
        assertEquals(newPrice, foundedAuto.get().getPrice());
        assertEquals(newManufacturer, foundedAuto.get().getManufacturer());
        assertEquals(newSpeed, foundedAuto.get().getSpeed());
        assertEquals(newYear, foundedAuto.get().getYear());
        assertEquals(newColor, foundedAuto.get().getColor());
        assertEquals(newRacingTires, foundedAuto.get().getRacingTires());
        assertEquals(newCount, foundedAuto.get().getCount());
    }

    @Test
    void deleteById_null() {
        target.save(sportCar);
        int expectedSize = 1;
        boolean actual = target.deleteById(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void deleteById() {
        target.save(sportCar);
        boolean actual = target.deleteById(sportCarId);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }

    @Test
    void delete_null() {
        target.save(sportCar);
        int expectedSize = 1;
        boolean actual = target.delete(null);
        assertFalse(actual);
        assertEquals(expectedSize, target.getAll().size());
    }

    @Test
    void delete() {
        target.save(sportCar);
        boolean actual = target.delete(sportCar);
        assertTrue(actual);
        assertTrue(target.getAll().isEmpty());
    }
}