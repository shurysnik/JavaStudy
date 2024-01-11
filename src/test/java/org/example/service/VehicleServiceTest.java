package org.example.service;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.model.Vehicle;
import org.example.reprository.AutoRepository;
import org.example.reprository.CrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class VehicleServiceTest {
    private VehicleService<Auto> target;
    protected AutoRepository repository;
    private Auto auto;

    public Auto createSimpleAuto() {
        return new Auto("Model", BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Type", 1);
    }

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AutoRepository.class);
        target = new VehicleService<>(repository) {
            @Override
            protected Auto create() {
                return createSimpleAuto();
            }
        };
        auto = createSimpleAuto();
    }

    @Test
    void createAndSaveAutos_negativeCount() {
        List<Auto> actual = target.createAndSaveAutos(-1);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(repository, times(0)).save(auto);
    }

    @Test
    void createAndSaveAutos_zeroCount() {
        List<Auto> actual = target.createAndSaveAutos(0);
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(repository, times(0)).save(auto);
    }

    @Test
    void createAndSaveAutos() {
        List<Auto> actual = target.createAndSaveAutos(5);
        final int expected = 5;
        Assertions.assertEquals(expected, actual.size());
        Mockito.verify(repository,times(5)).save(Mockito.any(Auto.class));
     }
    @Test
    void saveAuto() {
        Mockito.when(repository.save(auto)).thenReturn(true);
        final boolean actual = target.save(auto);
        Assertions.assertTrue(actual);
        Mockito.verify(repository,times(1)).save(auto);
    }
}