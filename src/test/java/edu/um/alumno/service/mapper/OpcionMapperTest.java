package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.OpcionAsserts.*;
import static edu.um.alumno.domain.OpcionTestSamples.*;
import static org.junit.Assert.*;

import edu.um.alumno.domain.Opcion;
import edu.um.alumno.service.dto.OpcionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcionMapperTest {

    private OpcionMapper opcionMapper;

    @BeforeEach
    void setUp() {
        opcionMapper = new OpcionMapperImpl();
    }

    @Test
    void testToDto() {
        Opcion opcion = getOpcionSample1();
        OpcionDTO opcionDTO = opcionMapper.toDto(opcion);

        assertNotNull(opcionDTO);
        assertEquals(opcion.getId(), opcionDTO.getId());
        assertEquals(opcion.getCodigo(), opcionDTO.getCodigo());
        assertEquals(opcion.getNombre(), opcionDTO.getNombre());
        assertEquals(opcion.getDescripcion(), opcionDTO.getDescripcion());
        assertEquals(opcion.getPrecioAdicional(), opcionDTO.getPrecioAdicional());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        OpcionDTO opcionDTO = getOpcionDTOSample1();
        Opcion opcion = opcionMapper.toEntity(opcionDTO);

        assertNotNull(opcion);
        assertEquals(opcionDTO.getId(), opcion.getId());
        assertEquals(opcionDTO.getCodigo(), opcion.getCodigo());
        assertEquals(opcionDTO.getNombre(), opcion.getNombre());
        assertEquals(opcionDTO.getDescripcion(), opcion.getDescripcion());
        assertEquals(opcionDTO.getPrecioAdicional(), opcion.getPrecioAdicional());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcionSample1();
        var actual = opcionMapper.toEntity(opcionMapper.toDto(expected));
        assertOpcionAllPropertiesEquals(expected, actual);
    }
}
