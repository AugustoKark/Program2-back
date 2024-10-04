package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.PersonalizacionAsserts.*;
import static edu.um.alumno.domain.PersonalizacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalizacionMapperTest {

    private PersonalizacionMapper personalizacionMapper;

    @BeforeEach
    void setUp() {
        personalizacionMapper = new PersonalizacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonalizacionSample1();
        var actual = personalizacionMapper.toEntity(personalizacionMapper.toDto(expected));
        assertPersonalizacionAllPropertiesEquals(expected, actual);
    }
}
