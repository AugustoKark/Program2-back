package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.CaracteristicaAsserts.*;
import static edu.um.alumno.domain.CaracteristicaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaracteristicaMapperTest {

    private CaracteristicaMapper caracteristicaMapper;

    @BeforeEach
    void setUp() {
        caracteristicaMapper = new CaracteristicaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaracteristicaSample1();
        var actual = caracteristicaMapper.toEntity(caracteristicaMapper.toDto(expected));
        assertCaracteristicaAllPropertiesEquals(expected, actual);
    }
}
