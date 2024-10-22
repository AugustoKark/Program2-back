package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.CaracteristicaAsserts.*;
import static edu.um.alumno.domain.CaracteristicaTestSamples.*;
import static org.junit.Assert.*;

import edu.um.alumno.domain.Caracteristica;
import edu.um.alumno.service.dto.CaracteristicaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaracteristicaMapperTest {

    private CaracteristicaMapper caracteristicaMapper;

    @BeforeEach
    void setUp() {
        caracteristicaMapper = new CaracteristicaMapperImpl();
    }

    @Test
    void testToDto() {
        Caracteristica caracteristica = getCaracteristicaSample1();
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        assertNotNull(caracteristicaDTO);
        assertEquals(caracteristica.getId(), caracteristicaDTO.getId());
        assertEquals(caracteristica.getNombre(), caracteristicaDTO.getNombre());
        assertEquals(caracteristica.getDescripcion(), caracteristicaDTO.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        CaracteristicaDTO caracteristicaDTO = getCaracteristicaDTOSample1();
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);

        assertNotNull(caracteristica);
        assertEquals(caracteristicaDTO.getId(), caracteristica.getId());
        assertEquals(caracteristicaDTO.getNombre(), caracteristica.getNombre());
        assertEquals(caracteristicaDTO.getDescripcion(), caracteristica.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaracteristicaSample1();
        var actual = caracteristicaMapper.toEntity(caracteristicaMapper.toDto(expected));
        assertCaracteristicaAllPropertiesEquals(expected, actual);
    }
}
