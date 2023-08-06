package org.example.service;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.reprository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


class AutoServiceTest {
    private AutoService target;
    private AutoRepository autoRepository;
    private Auto auto;
    private String autoId;

    public Auto createSimpleAuto() {
        return new Auto("Model", BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Type");
    }

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
        auto = createSimpleAuto();
        autoId = auto.getId();
    }

    @Test
    void createAndSaveAutos_negativeCount() {
        List<Auto> actual = target.createAndSaveAutos(-1);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(autoRepository, times(0)).save(Mockito.any());
    }

    @Test
    void createAndSaveAutos_zeroCount() {
        List<Auto> actual = target.createAndSaveAutos(0);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(autoRepository, times(0)).save(Mockito.any());
    }

    @Test
    void findOneByIdByCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById("404");
        Mockito.verify(autoRepository).getById(captor.capture());
        assertEquals("404", captor.getValue());
    }

    @Test
    void findOneByIdNullByCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(autoRepository).getById(captor.capture());
        assertEquals("", captor.getValue());
    }

    @Test
    void findOneByIdArgumentMatcherHospitalCar() {
        ArgumentMatcher<String> matcher = string -> string.startsWith("404");
        String id = "404232";
        target.findOneById(id);
        assertTrue(matcher.matches(id));
    }

    @Test
    void createAndSaveAutos() {
        List<Auto> actual = target.createAndSaveAutos(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(autoRepository, times(5)).save(Mockito.any());
    }

    @Test
    void update_negativeTest() {
        Mockito.when(autoRepository.getById("Model-1")).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.update(null), "Auto must be not null");
        Mockito.verify(autoRepository, never()).update(auto);
    }

    @Test
    void update_positiveTest() {
        Mockito.when(autoRepository.update(auto)).thenReturn(true);
        final Auto actual = target.update(auto);
        Mockito.when(autoRepository.save(auto)).thenReturn(true);
        Assertions.assertEquals("Model", actual.getModel());
        Assertions.assertEquals(BigDecimal.ONE, actual.getPrice());
        Assertions.assertEquals(Manufacturer.MAZDA, actual.getManufacturer());
        Assertions.assertEquals(RacingTires.SLICKS, actual.getRacingTires());
        Assertions.assertEquals("Model", actual.getBodyType());
        Mockito.verify(autoRepository, times(1)).save(Mockito.any());
    }

    @Test
    void findById_withEmptyId() {
        Mockito.when(autoRepository.getById("")).thenReturn(auto);
        final Auto actual = target.findOneById("");
        Assertions.assertEquals(actual.getId(), auto.getId());
        Mockito.verify(autoRepository, times(1)).getById("");
    }

    @Test
    void findById() {
        Mockito.when(autoRepository.getById(autoId)).thenReturn(auto);
        final Auto actual = target.findOneById(autoId);
        Assertions.assertEquals(actual.getId(), autoId);
        Mockito.verify(autoRepository, times(1)).getById(autoId);
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }

    @Test
    void saveAuto() {
        Mockito.when(autoRepository.save(auto)).thenReturn(true);
        final boolean actual = target.saveAuto(auto);
        Assertions.assertTrue(actual);
    }

    @Test
    void saveAuto_null() {
        Mockito.when(autoRepository.save(null)).thenReturn(false);
        final boolean actual = target.saveAuto(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void delete() {
        Mockito.when(autoRepository.deleteById(autoId)).thenReturn(true);
        final boolean actual = target.deleteById(autoId);
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_null() {
        final boolean actual = target.delete(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void deleteAutos() {
        Mockito.when(autoRepository.delete(auto)).thenReturn(true);
        final boolean actual = target.delete(auto);
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteAutos_null() {
        final boolean actual = target.delete(null);
        Assertions.assertFalse(actual);
    }
    @Test
    void saveAutos() {
        List<Auto> autos = List.of(createSimpleAuto());
        Mockito.when(autoRepository.saveAll(autos)).thenReturn(true);
        final boolean actual = target.saveAutos(autos);
        Assertions.assertTrue(actual);
    }
    @Test
    void saveAutos_null() {
        Mockito.when(autoRepository.saveAll(null)).thenReturn(false);
        final boolean actual = target.saveAutos(null);
        Assertions.assertFalse(actual);
        Mockito.verify(autoRepository, times(1)).saveAll(null);
    }
}


































