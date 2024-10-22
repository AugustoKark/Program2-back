package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Venta;
import edu.um.alumno.repository.VentaRepository;
import edu.um.alumno.service.dto.VentaDTO;
import edu.um.alumno.service.mapper.VentaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private VentaMapper ventaMapper;

    @InjectMocks
    private VentaService ventaService;

    private Venta venta;
    private VentaDTO ventaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        venta = new Venta();
        venta.setId(1L);
        venta.setFechaVenta(ZonedDateTime.now());
        venta.setPrecioFinal(new BigDecimal("100.00"));

        ventaDTO = new VentaDTO();
        ventaDTO.setId(1L);
        ventaDTO.setFechaVenta(ZonedDateTime.now());
        ventaDTO.setPrecioFinal(new BigDecimal("100.00"));
    }

    @Test
    void testSave() {
        when(ventaMapper.toEntity(any(VentaDTO.class))).thenReturn(venta);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        VentaDTO result = ventaService.save(ventaDTO);

        assertNotNull(result);
        assertEquals(ventaDTO.getId(), result.getId());
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testUpdate() {
        when(ventaMapper.toEntity(any(VentaDTO.class))).thenReturn(venta);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        VentaDTO result = ventaService.update(ventaDTO);

        assertNotNull(result);
        assertEquals(ventaDTO.getId(), result.getId());
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testPartialUpdate() {
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        Optional<VentaDTO> result = ventaService.partialUpdate(ventaDTO);

        assertTrue(result.isPresent());
        assertEquals(ventaDTO.getId(), result.get().getId());
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void testFindAll() {
        Page<Venta> page = new PageImpl<>(Collections.singletonList(venta));
        when(ventaRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        Page<VentaDTO> result = ventaService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(ventaRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindOne() {
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        Optional<VentaDTO> result = ventaService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(ventaDTO.getId(), result.get().getId());
        verify(ventaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        doNothing().when(ventaRepository).deleteById(anyLong());

        ventaService.delete(1L);

        verify(ventaRepository, times(1)).deleteById(anyLong());
    }
}
