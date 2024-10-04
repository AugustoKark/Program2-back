package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.OpcionAsserts.*;
import static edu.um.alumno.domain.OpcionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcionMapperTest {

    private OpcionMapper opcionMapper;

    @BeforeEach
    void setUp() {
        opcionMapper = new OpcionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcionSample1();
        var actual = opcionMapper.toEntity(opcionMapper.toDto(expected));
        assertOpcionAllPropertiesEquals(expected, actual);
    }
}
