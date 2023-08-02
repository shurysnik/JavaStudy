package org.example.service;

import org.example.model.Color;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.model.SportCar;
import org.example.reprository.SportCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;


class SportCarServiceTest {
    private SportCarService target;
    private SportCarRepository sportCarRepository;
    private SportCar sportCar;

    @BeforeEach
    void setUp() {
        sportCarRepository = Mockito.mock(SportCarRepository.class);
        target = new SportCarService(sportCarRepository);
        sportCar = createSimpleSportCar();
    }

    public SportCar createSimpleSportCar() {
        return new SportCar("Model", BigDecimal.ZERO, Manufacturer.TOYOTA,
                RacingTires.RACING, BigDecimal.ZERO, 2000, Color.BLACK);
    }

    @Test
    void createAndSaveAutos_negativeCount() {
        final List<SportCar> actual = target.createAndSaveAutos(-1);
        final int expected = 0;
        Mockito.verify(sportCarRepository, Mockito.times(0)).save(Mockito.any());
        Assertions.assertEquals(expected, actual.size());
    }

    @Test
    void createAndSaveAutos_zeroCount() {
        final List<SportCar> actual = target.createAndSaveAutos(0);
        final int expected = 0;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void createAndSaveAutos() {
        final List<SportCar> actual = target.createAndSaveAutos(5);
        final int expected = 5;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(5)).save(Mockito.any());
    }

    @Test
    void findById_negativeTest() {
        Mockito.when(sportCarRepository.getById("")).thenReturn(sportCar);
        final SportCar actual = target.findOneById("");
        Assertions.assertEquals(actual.getId(), sportCar.getId());
        Mockito.verify(sportCarRepository, Mockito.times(1)).getById("");
    }

    @Test
    void findById_positiveTest() {
        Mockito.when(sportCarRepository.getById(sportCar.getId())).thenReturn(sportCar);
        final SportCar actual = target.findOneById(sportCar.getId());
        Assertions.assertEquals(actual.getId(), sportCar.getId());
        Mockito.verify(sportCarRepository, Mockito.times(1)).getById(sportCar.getId());
    }

    @Test
    void printAll() {
        Mockito.when(sportCarRepository.getAll()).thenReturn(List.of(createSimpleSportCar()));
        target.printAll();
        Mockito.verify(sportCarRepository, Mockito.times(1)).getAll();
    }

    @Test
    void saveAuto() {
        Mockito.when(sportCarRepository.save(sportCar)).thenReturn(true);
        final boolean actual = target.saveAuto(sportCar);
        Assertions.assertNotNull(sportCar);
        Assertions.assertEquals(BigDecimal.valueOf(-1), sportCar.getPrice());
        sportCar.setPrice(BigDecimal.ONE);

        Mockito.when(sportCarRepository.save(sportCar)).thenReturn(true);
        final boolean actualNewSportCar = target.saveAuto(sportCar);
        Assertions.assertNotNull(sportCar);
        Assertions.assertEquals(BigDecimal.ONE, sportCar.getPrice());
    }

    @Test
    void saveAuto_null() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.saveAuto(null), "Sport car must not be null");
    }

    @Test
    void saveAutosFalse() {
        List<SportCar> sportCarList = new LinkedList<>();
        Mockito.when(sportCarRepository.saveAll(sportCarList)).thenReturn(false);
        final boolean actual = target.saveAutos(sportCarList);
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAutosTrue() {
        Mockito.when(sportCarRepository.saveAll(List.of(sportCar))).thenReturn(false);
        final boolean actual = target.saveAutos(List.of(sportCar));
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAutos_null() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.saveAutos(null), "Sport car must not be null");
    }

    @Test
    void deleteById() {
        Mockito.when(sportCarRepository.deleteById(sportCar.getId())).thenReturn(true);
        final boolean actual = target.deleteById(sportCar.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteById_null() {
        Mockito.when(sportCarRepository.deleteById(sportCar.getId())).thenReturn(false);
        final boolean actual = target.deleteById(sportCar.getId());
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteAutos() {
        Mockito.when(sportCarRepository.delete(sportCar)).thenReturn(true);
        final boolean actual = target.delete(sportCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteAutos_null() {
        Mockito.when(sportCarRepository.delete(sportCar)).thenReturn(false);
        final boolean actual = target.delete(sportCar);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_negativeTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.update(null), "Sport car must be not null");
    }

    @Test
    void update_positiveTest() {
        Mockito.when(sportCarRepository.update(sportCar)).thenReturn(true);
        final SportCar actual = target.update(sportCar);
        Mockito.when(sportCarRepository.save(sportCar)).thenReturn(true);
        Assertions.assertEquals("Model", actual.getModel());
        Assertions.assertEquals(BigDecimal.ONE, actual.getPrice());
        Assertions.assertEquals(Manufacturer.MAZDA, actual.getManufacturer());
        Assertions.assertEquals(RacingTires.SLICKS, actual.getRacingTires());
        Assertions.assertEquals(BigDecimal.ONE, actual.getSpeed());
        Assertions.assertEquals(2010, actual.getYear());
        Assertions.assertEquals(Color.YELLOW, actual.getColor());
        Mockito.verify(sportCarRepository, times(1)).save(Mockito.any());
    }
}

