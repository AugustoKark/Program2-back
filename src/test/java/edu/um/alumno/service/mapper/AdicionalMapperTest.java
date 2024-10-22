package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.AdicionalAsserts.*;
import static edu.um.alumno.domain.AdicionalTestSamples.*;
import static org.junit.Assert.*;

import edu.um.alumno.domain.Adicional;
import edu.um.alumno.service.dto.AdicionalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AdicionalMapperTest {

    private AdicionalMapper adicionalMapper;

    @BeforeEach
    void setUp() {
        adicionalMapper = new AdicionalMapperImpl();
    }

    @Test
    void testToDto() {
        Adicional adicional = getAdicionalSample1();
        AdicionalDTO adicionalDTO = adicionalMapper.toDto(adicional);

        assertNotNull(adicionalDTO);
        assertEquals(adicional.getId(), adicionalDTO.getId());
        assertEquals(adicional.getNombre(), adicionalDTO.getNombre());
        assertEquals(adicional.getDescripcion(), adicionalDTO.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        AdicionalDTO adicionalDTO = new AdicionalDTO();
        adicionalDTO.setId(1L);
        adicionalDTO.setNombre("nombre1");
        adicionalDTO.setDescripcion("descripcion1");

        Adicional adicional = adicionalMapper.toEntity(adicionalDTO);

        assertNotNull(adicional);
        assertEquals(adicionalDTO.getId(), adicional.getId());
        assertEquals(adicionalDTO.getNombre(), adicional.getNombre());
        assertEquals(adicionalDTO.getDescripcion(), adicional.getDescripcion());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalSample1();
        var actual = adicionalMapper.toEntity(adicionalMapper.toDto(expected));
        assertAdicionalAllPropertiesEquals(expected, actual);
    }
}
