package edu.um.alumno.service.mapper;

import static edu.um.alumno.domain.DispositivoAsserts.*;
import static edu.um.alumno.domain.DispositivoTestSamples.*;
import static org.junit.Assert.*;

import edu.um.alumno.domain.Dispositivo;
import edu.um.alumno.service.dto.CaracteristicaDTO;
import edu.um.alumno.service.dto.DispositivoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositivoMapperTest {

    private DispositivoMapper dispositivoMapper;

    @BeforeEach
    void setUp() {
        dispositivoMapper = new DispositivoMapperImpl();
    }

    @Test
    void testToDto() {
        Dispositivo dispositivo = getDispositivoSample1();
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        assertNotNull(dispositivoDTO);
        assertEquals(dispositivo.getId(), dispositivoDTO.getId());
        assertEquals(dispositivo.getCodigo(), dispositivoDTO.getCodigo());
        assertEquals(dispositivo.getNombre(), dispositivoDTO.getNombre());
        assertEquals(dispositivo.getDescripcion(), dispositivoDTO.getDescripcion());
        assertEquals(dispositivo.getPrecioBase(), dispositivoDTO.getPrecioBase());
        assertEquals(dispositivo.getMoneda(), dispositivoDTO.getMoneda());
        // Add more assertions to verify all properties
    }

    @Test
    void testToEntity() {
        DispositivoDTO dispositivoDTO = getDispositivoDTOSample1();
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);

        assertNotNull(dispositivo);
        assertEquals(dispositivoDTO.getId(), dispositivo.getId());
        assertEquals(dispositivoDTO.getCodigo(), dispositivo.getCodigo());
        assertEquals(dispositivoDTO.getNombre(), dispositivo.getNombre());
        assertEquals(dispositivoDTO.getDescripcion(), dispositivo.getDescripcion());
        assertEquals(dispositivoDTO.getPrecioBase(), dispositivo.getPrecioBase());
        assertEquals(dispositivoDTO.getMoneda(), dispositivo.getMoneda());
        // Add more assertions to verify all properties
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDispositivoSample1();
        var actual = dispositivoMapper.toEntity(dispositivoMapper.toDto(expected));
        assertDispositivoAllPropertiesEquals(expected, actual);
    }
}
