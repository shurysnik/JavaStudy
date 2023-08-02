package org.example.service;

import org.example.model.CivilCar;
import org.example.model.FuelType;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.reprository.CivilCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

class CivilCarServiceTest {

    private CivilCarService target;
    private CivilCarRepository civilCarRepository;
    private CivilCar civilCar;

    @BeforeEach
    void setUp() {
        civilCarRepository = Mockito.mock(CivilCarRepository.class);
        target = new CivilCarService(civilCarRepository);
        civilCar = createSimpleCivilCar();
    }

    public CivilCar createSimpleCivilCar() {
        return new CivilCar("Model", BigDecimal.ONE, Manufacturer.AUDI,
                RacingTires.ROAD, 1.1, FuelType.HYBRID);
    }

    @Test
    void createAndSaveAutos_negativeCount() {
        final List<CivilCar> actual = target.createAndSaveAutos(-1);
        final int expected = 0;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(civilCarRepository, Mockito.times(0)).save(civilCar);
    }

    @Test
    void createAndSaveAutos_zeroCount() {
        final List<CivilCar> actual = target.createAndSaveAutos(0);
        final int expected = 0;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(civilCarRepository, Mockito.times(0)).save(civilCar);
    }

    @Test
    void createAndSaveAutos() {
        final List<CivilCar> actual = target.createAndSaveAutos(5);
        final int expected = 5;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(civilCarRepository, times(5)).save(Mockito.any());
    }

    @Test
    void findById_null() {
        Mockito.when(civilCarRepository.update(null)).thenReturn(false);
        final CivilCar actual = target.findById(null);
        Assertions.assertNull(actual);
    }

    @Test
    void findById_negativeTest() {
        Mockito.when(civilCarRepository.getById("")).thenReturn(civilCar);
        final CivilCar actual = target.findById("");
        Assertions.assertEquals(actual.getId(), civilCar.getId());
        Mockito.verify(civilCarRepository, times(1)).getById("");
    }

    @Test
    void findById_positiveTest() {
        Mockito.when(civilCarRepository.getById(civilCar.getId())).thenReturn(civilCar);
        final CivilCar actual = target.findById(civilCar.getId());
        Assertions.assertEquals(actual.getId(), civilCar.getId());
        Mockito.verify(civilCarRepository, times(1)).getById(civilCar.getId());
    }

    @Test
    void printAll() {
        Mockito.when(civilCarRepository.getAll()).thenReturn(List.of(civilCar));
        target.printAll();
    }

    @Test
    void saveAuto() {
        Mockito.when(civilCarRepository.save(civilCar)).thenReturn(true);
        final boolean actual = target.saveAuto(civilCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void saveAuto_null() {
        Mockito.when(civilCarRepository.save(null)).thenReturn(false);
        final boolean actual = target.saveAuto(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAutos() {
        Mockito.when(civilCarRepository.saveAll(List.of(civilCar))).thenReturn(true);
        final boolean actual = target.saveAutos(List.of(civilCar));
        Assertions.assertTrue(actual);
    }

    @Test
    void saveAutos_null() {
        Mockito.when(civilCarRepository.saveAll(null)).thenReturn(true);
        final boolean actual = target.saveAutos(null);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteById() {
        Mockito.when(civilCarRepository.deleteById(civilCar.getId())).thenReturn(true);
        final boolean actual = target.deleteById(civilCar.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteById_null() {
        Mockito.when(civilCarRepository.deleteById(null)).thenReturn(true);
        final boolean actual = target.deleteById(null);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteAutos() {
        Mockito.when(civilCarRepository.delete(civilCar)).thenReturn(true);
        final boolean actual = target.delete(civilCar);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteAutos_null() {
        Mockito.when(civilCarRepository.delete(null)).thenReturn(true);
        final boolean actual = target.delete(null);
        Assertions.assertTrue(actual);
    }

    @Test
    void update_negativeTest() {
        Mockito.when(civilCarRepository.getById("Model 2")).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.update(null), "Civil auto must be not null");
        Mockito.verify(civilCarRepository, never()).update(civilCar);
    }

    @Test
    void update_positiveTest() {
        Mockito.when(civilCarRepository.update(civilCar)).thenReturn(true);
        final CivilCar actual = target.update(civilCar);
        Mockito.when(civilCarRepository.save(civilCar)).thenReturn(true);
        Assertions.assertEquals("Model", actual.getModel());
        Assertions.assertEquals(BigDecimal.ONE, actual.getPrice());
        Assertions.assertEquals(Manufacturer.MAZDA, actual.getManufacturer());
        Assertions.assertEquals(RacingTires.SLICKS, actual.getRacingTires());
        Assertions.assertEquals(0.0, actual.getFuelConsumption());
        Assertions.assertEquals(FuelType.PETROL, actual.getFuelType());
        Mockito.verify(civilCarRepository, times(1)).save(Mockito.any());
    }
}

