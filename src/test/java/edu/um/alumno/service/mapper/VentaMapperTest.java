package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.VentaAsserts.*;
import static edu.um.alumno.domain.VentaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VentaMapperTest {

    private VentaMapper ventaMapper;

    @BeforeEach
    void setUp() {
        ventaMapper = new VentaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVentaSample1();
        var actual = ventaMapper.toEntity(ventaMapper.toDto(expected));
        assertVentaAllPropertiesEquals(expected, actual);
    }
}
