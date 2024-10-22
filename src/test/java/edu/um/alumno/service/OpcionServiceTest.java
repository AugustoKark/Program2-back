package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Opcion;
import edu.um.alumno.repository.OpcionRepository;
import edu.um.alumno.service.dto.OpcionDTO;
import edu.um.alumno.service.mapper.OpcionMapper;
import java.math.BigDecimal;
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

class OpcionServiceTest {

    @Mock
    private OpcionRepository opcionRepository;

    @Mock
    private OpcionMapper opcionMapper;

    @InjectMocks
    private OpcionService opcionService;

    private Opcion opcion;
    private OpcionDTO opcionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        opcion = new Opcion();
        opcion.setId(1L);
        opcion.setCodigo("OP001");
        opcion.setNombre("Opción 1");
        opcion.setDescripcion("Descripción de la opción 1");
        opcion.setPrecioAdicional(new BigDecimal("50.00"));

        opcionDTO = new OpcionDTO();
        opcionDTO.setId(1L);
        opcionDTO.setCodigo("OP001");
        opcionDTO.setNombre("Opción 1");
        opcionDTO.setDescripcion("Descripción de la opción 1");
        opcionDTO.setPrecioAdicional(new BigDecimal("50.00"));
    }

    @Test
    void testSave() {
        when(opcionMapper.toEntity(any(OpcionDTO.class))).thenReturn(opcion);
        when(opcionRepository.save(any(Opcion.class))).thenReturn(opcion);
        when(opcionMapper.toDto(any(Opcion.class))).thenReturn(opcionDTO);

        OpcionDTO result = opcionService.save(opcionDTO);

        assertNotNull(result);
        assertEquals(opcionDTO.getId(), result.getId());
        verify(opcionRepository, times(1)).save(any(Opcion.class));
    }

    @Test
    void testUpdate() {
        when(opcionMapper.toEntity(any(OpcionDTO.class))).thenReturn(opcion);
        when(opcionRepository.save(any(Opcion.class))).thenReturn(opcion);
        when(opcionMapper.toDto(any(Opcion.class))).thenReturn(opcionDTO);

        OpcionDTO result = opcionService.update(opcionDTO);

        assertNotNull(result);
        assertEquals(opcionDTO.getId(), result.getId());
        verify(opcionRepository, times(1)).save(any(Opcion.class));
    }

    @Test
    void testPartialUpdate() {
        when(opcionRepository.findById(anyLong())).thenReturn(Optional.of(opcion));
        when(opcionRepository.save(any(Opcion.class))).thenReturn(opcion);
        when(opcionMapper.toDto(any(Opcion.class))).thenReturn(opcionDTO);

        Optional<OpcionDTO> result = opcionService.partialUpdate(opcionDTO);

        assertTrue(result.isPresent());
        assertEquals(opcionDTO.getId(), result.get().getId());
        verify(opcionRepository, times(1)).save(any(Opcion.class));
    }

    @Test
    void testFindAll() {
        Page<Opcion> page = new PageImpl<>(Collections.singletonList(opcion));
        when(opcionRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(opcionMapper.toDto(any(Opcion.class))).thenReturn(opcionDTO);

        Page<OpcionDTO> result = opcionService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(opcionRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindOne() {
        when(opcionRepository.findById(anyLong())).thenReturn(Optional.of(opcion));
        when(opcionMapper.toDto(any(Opcion.class))).thenReturn(opcionDTO);

        Optional<OpcionDTO> result = opcionService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(opcionDTO.getId(), result.get().getId());
        verify(opcionRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        doNothing().when(opcionRepository).deleteById(anyLong());

        opcionService.delete(1L);

        verify(opcionRepository, times(1)).deleteById(anyLong());
    }
}
