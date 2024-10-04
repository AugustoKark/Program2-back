package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.AdicionalAsserts.*;
import static edu.um.alumno.domain.AdicionalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdicionalMapperTest {

    private AdicionalMapper adicionalMapper;

    @BeforeEach
    void setUp() {
        adicionalMapper = new AdicionalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalSample1();
        var actual = adicionalMapper.toEntity(adicionalMapper.toDto(expected));
        assertAdicionalAllPropertiesEquals(expected, actual);
    }
}
