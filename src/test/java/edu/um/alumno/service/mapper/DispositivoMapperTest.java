package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.DispositivoAsserts.*;
import static edu.um.alumno.domain.DispositivoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositivoMapperTest {

    private DispositivoMapper dispositivoMapper;

    @BeforeEach
    void setUp() {
        dispositivoMapper = new DispositivoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositivoSample1();
        var actual = dispositivoMapper.toEntity(dispositivoMapper.toDto(expected));
        assertDispositivoAllPropertiesEquals(expected, actual);
    }
}
