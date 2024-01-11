package org.example.reprository;

import org.example.model.Color;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.model.SportCar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class SportCarRepositoryTest {

    private SportCarRepository target;
    private SportCar sportCar;
    private String sportCarId;

    public SportCar createSimpleSportCar() {
        return new SportCar("Model", BigDecimal.ZERO, Manufacturer.HYUNDAI,
                RacingTires.RACING, BigDecimal.ZERO, 2000, Color.BLACK,1);
    }

    @BeforeEach
    void setUp() {
        target = new SportCarRepository();
        sportCar = createSimpleSportCar();
        target.save(sportCar);
        sportCarId = sportCar.getId();
    }

    @Test
    void getById_findOne() {
        final SportCar actual = target.getById(sportCarId);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getId(), sportCarId);
    }

    @Test
    void getById_notFindNull() {
        final SportCar actual = target.getById(null);
        Assertions.assertNull(actual);
    }

    @Test
    void getById_notFindNothing() {
        final SportCar actual = target.getById("nothing");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_manySportCars() {
        final SportCar otherSportCarAuto = createSimpleSportCar();
        final SportCar actualFirstSportCarAuto = target.getById(sportCarId);
        Assertions.assertNotNull(actualFirstSportCarAuto);

        final boolean savedSportCarAuto = target.save(otherSportCarAuto);
        Assertions.assertTrue(savedSportCarAuto);

        final SportCar actualSecondSportCarAuto = target.getById(otherSportCarAuto.getId());
        Assertions.assertNotNull(actualSecondSportCarAuto);

        Assertions.assertEquals(actualFirstSportCarAuto.getId(), sportCarId);
        Assertions.assertNotEquals(actualFirstSportCarAuto.getId(), otherSportCarAuto.getId());

        Assertions.assertEquals(actualSecondSportCarAuto.getId(), otherSportCarAuto.getId());
        Assertions.assertNotEquals(actualSecondSportCarAuto.getId(), sportCarId);

    }

    @Test
    void getAll() {
        final List<SportCar> actual = target.getAll();
        final int expectedOne = 1;
        final int expectedTwo = 2;
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedOne, actual.size());
        target.saveAll(actual);
        Assertions.assertEquals(expectedTwo, actual.size());
    }

    @Test
    void getAll_NotEmptyList() {
        final List<SportCar> actual = target.getAll();
        Assertions.assertNotNull(actual);
        final List<SportCar> expected = new LinkedList<>();
        Assertions.assertNotEquals(actual, expected);

    }

    @Test
    void getAll_EmptyList() {
        target.delete(sportCar);
        final List<SportCar> actual = target.getAll();
        Assertions.assertNotNull(actual);
        final List<SportCar> expected = new LinkedList<>();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getAll_checkListSize() {
        final List<SportCar> actual = target.getAll();
        Assertions.assertNotNull(actual);
        final int expectedSize = 1;
        Assertions.assertEquals(expectedSize, actual.size());

    }

    @Test
    void save_success() {
        final boolean actual = target.save(sportCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        Assertions.assertEquals(BigDecimal.valueOf(-1), sportCar.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        sportCar.setPrice(BigDecimal.TEN);
        target.save(sportCar);
        final SportCar expected = target.getById(sportCarId);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getPrice(), sportCar.getPrice());
    }

    @Test
    void saveAll() {
        final boolean actual = target.saveAll(List.of(createSimpleSportCar()));
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
        boolean actual = target.update(createSimpleSportCar());
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        sportCar.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(sportCar);
        Assertions.assertTrue(actual);
        final SportCar expected = target.getById(sportCarId);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getPrice(), sportCar.getPrice());
    }

    @Test
    void updateByYear() {
        final SportCar otherSportCar = createSimpleSportCar();
        Assertions.assertNotNull(otherSportCar);
        sportCar.setPrice(BigDecimal.TEN);
        sportCar.setManufacturer(Manufacturer.TOYOTA);
        final boolean actual = target.updateByYear(otherSportCar.getYear(), sportCar);
        Assertions.assertTrue(actual);
        Assertions.assertEquals(otherSportCar.getYear(), sportCar.getYear());
        Assertions.assertNotEquals(otherSportCar.getManufacturer(), sportCar.getManufacturer());
    }

    @Test
    void delete() {
        final boolean actual = target.deleteById(sportCarId);
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final boolean actual = target.deleteById("nothing");
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteSportCar() {
        final boolean actual = target.delete(sportCar);
        Assertions.assertTrue(actual);
    }
}