package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Caracteristica;
import edu.um.alumno.repository.CaracteristicaRepository;
import edu.um.alumno.service.dto.CaracteristicaDTO;
import edu.um.alumno.service.mapper.CaracteristicaMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class CaracteristicaServiceTest {

    @Mock
    private CaracteristicaRepository caracteristicaRepository;

    @Mock
    private CaracteristicaMapper caracteristicaMapper;

    @InjectMocks
    private CaracteristicaService caracteristicaService;

    private Caracteristica caracteristica;
    private CaracteristicaDTO caracteristicaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caracteristica = new Caracteristica();
        caracteristica.setId(1L);
        caracteristica.setNombre("Caracteristica 1");
        caracteristica.setDescripcion("Descripción de la caracteristica 1");

        caracteristicaDTO = new CaracteristicaDTO();
        caracteristicaDTO.setId(1L);
        caracteristicaDTO.setNombre("Caracteristica 1");
        caracteristicaDTO.setDescripcion("Descripción de la caracteristica 1");
    }

    @Test
    void testSave() {
        when(caracteristicaMapper.toEntity(any(CaracteristicaDTO.class))).thenReturn(caracteristica);
        when(caracteristicaRepository.save(any(Caracteristica.class))).thenReturn(caracteristica);
        when(caracteristicaMapper.toDto(any(Caracteristica.class))).thenReturn(caracteristicaDTO);

        CaracteristicaDTO result = caracteristicaService.save(caracteristicaDTO);

        assertNotNull(result);
        assertEquals(caracteristicaDTO.getId(), result.getId());
        verify(caracteristicaRepository, times(1)).save(any(Caracteristica.class));
    }

    @Test
    void testUpdate() {
        when(caracteristicaMapper.toEntity(any(CaracteristicaDTO.class))).thenReturn(caracteristica);
        when(caracteristicaRepository.save(any(Caracteristica.class))).thenReturn(caracteristica);
        when(caracteristicaMapper.toDto(any(Caracteristica.class))).thenReturn(caracteristicaDTO);

        CaracteristicaDTO result = caracteristicaService.update(caracteristicaDTO);

        assertNotNull(result);
        assertEquals(caracteristicaDTO.getId(), result.getId());
        verify(caracteristicaRepository, times(1)).save(any(Caracteristica.class));
    }

    @Test
    void testPartialUpdate() {
        when(caracteristicaRepository.findById(anyLong())).thenReturn(Optional.of(caracteristica));
        when(caracteristicaRepository.save(any(Caracteristica.class))).thenReturn(caracteristica);
        when(caracteristicaMapper.toDto(any(Caracteristica.class))).thenReturn(caracteristicaDTO);

        Optional<CaracteristicaDTO> result = caracteristicaService.partialUpdate(caracteristicaDTO);

        assertTrue(result.isPresent());
        assertEquals(caracteristicaDTO.getId(), result.get().getId());
        verify(caracteristicaRepository, times(1)).save(any(Caracteristica.class));
    }

    @Test
    void testFindAll() {
        List<Caracteristica> caracteristicas = Collections.singletonList(caracteristica);
        when(caracteristicaRepository.findAll()).thenReturn(caracteristicas);
        when(caracteristicaMapper.toDto(any(Caracteristica.class))).thenReturn(caracteristicaDTO);

        List<CaracteristicaDTO> result = caracteristicaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(caracteristicaRepository, times(1)).findAll();
    }

    @Test
    void testFindOne() {
        when(caracteristicaRepository.findById(anyLong())).thenReturn(Optional.of(caracteristica));
        when(caracteristicaMapper.toDto(any(Caracteristica.class))).thenReturn(caracteristicaDTO);

        Optional<CaracteristicaDTO> result = caracteristicaService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(caracteristicaDTO.getId(), result.get().getId());
        verify(caracteristicaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        doNothing().when(caracteristicaRepository).deleteById(anyLong());

        caracteristicaService.delete(1L);

        verify(caracteristicaRepository, times(1)).deleteById(anyLong());
    }
}
