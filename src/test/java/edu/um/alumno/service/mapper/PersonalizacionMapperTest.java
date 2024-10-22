package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.PersonalizacionAsserts.*;
import static edu.um.alumno.domain.PersonalizacionTestSamples.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.um.alumno.domain.Personalizacion;
import edu.um.alumno.service.dto.PersonalizacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PersonalizacionMapperTest {

    private PersonalizacionMapper personalizacionMapper;

    @BeforeEach
    void setUp() {
        personalizacionMapper = Mappers.getMapper(PersonalizacionMapper.class);
    }

    @Test
    void testToDto() {
        Personalizacion personalizacion = getPersonalizacionSample1();
        PersonalizacionDTO personalizacionDTO = personalizacionMapper.toDto(personalizacion);

        assertNotNull(personalizacionDTO);
        assertEquals(personalizacion.getId(), personalizacionDTO.getId());
        assertEquals(personalizacion.getNombre(), personalizacionDTO.getNombre());
        assertEquals(personalizacion.getDescripcion(), personalizacionDTO.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        PersonalizacionDTO personalizacionDTO = getPersonalizacionDTOSample1();
        Personalizacion personalizacion = personalizacionMapper.toEntity(personalizacionDTO);

        assertNotNull(personalizacion);
        assertEquals(personalizacionDTO.getId(), personalizacion.getId());
        assertEquals(personalizacionDTO.getNombre(), personalizacion.getNombre());
        assertEquals(personalizacionDTO.getDescripcion(), personalizacion.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonalizacionSample1();
        var actual = personalizacionMapper.toEntity(personalizacionMapper.toDto(expected));
        assertPersonalizacionAllPropertiesEquals(expected, actual);
    }
}
