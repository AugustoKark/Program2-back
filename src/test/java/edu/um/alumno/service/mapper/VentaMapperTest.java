package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.VentaAsserts.*;
import static edu.um.alumno.domain.VentaTestSamples.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.um.alumno.domain.Venta;
import edu.um.alumno.service.dto.VentaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class VentaMapperTest {

    private VentaMapper ventaMapper;

    @BeforeEach
    void setUp() {
        ventaMapper = Mappers.getMapper(VentaMapper.class);
    }

    @Test
    void testToDto() {
        Venta venta = getVentaSample1();
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        assertNotNull(ventaDTO);
        assertEquals(venta.getId(), ventaDTO.getId());
        assertEquals(venta.getFechaVenta(), ventaDTO.getFechaVenta());
        assertEquals(venta.getPrecioFinal(), ventaDTO.getPrecioFinal());
        assertEquals(venta.getUser().getId(), ventaDTO.getUser().getId());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        VentaDTO ventaDTO = getVentaDTOSample1();
        Venta venta = ventaMapper.toEntity(ventaDTO);

        assertNotNull(venta);
        assertEquals(ventaDTO.getId(), venta.getId());
        assertEquals(ventaDTO.getFechaVenta(), venta.getFechaVenta());
        assertEquals(ventaDTO.getPrecioFinal(), venta.getPrecioFinal());
        assertEquals(ventaDTO.getUser().getId(), venta.getUser().getId());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVentaSample1();
        var actual = ventaMapper.toEntity(ventaMapper.toDto(expected));
        assertVentaAllPropertiesEquals(expected, actual);
    }
}
