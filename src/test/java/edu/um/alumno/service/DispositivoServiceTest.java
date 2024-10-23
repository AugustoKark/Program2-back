package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Dispositivo;
import edu.um.alumno.repository.DispositivoRepository;
import edu.um.alumno.service.dto.DispositivoDTO;
import edu.um.alumno.service.mapper.DispositivoMapper;
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
import org.springframework.data.domain.Pageable;

class DispositivoServiceTest {

    @Mock
    private DispositivoRepository dispositivoRepository;

    @Mock
    private DispositivoMapper dispositivoMapper;

    @InjectMocks
    private DispositivoService dispositivoService;

    private Dispositivo dispositivo;
    private DispositivoDTO dispositivoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dispositivo = new Dispositivo();
        dispositivo.setId(1L);
        dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setId(1L);
    }

    @Test
    void testSaveDispositivo() {
        when(dispositivoMapper.toEntity(dispositivoDTO)).thenReturn(dispositivo);
        when(dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        DispositivoDTO result = dispositivoService.save(dispositivoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dispositivoRepository, times(1)).save(dispositivo);
    }

    @Test
    void testUpdateDispositivo() {
        when(dispositivoMapper.toEntity(dispositivoDTO)).thenReturn(dispositivo);
        when(dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        DispositivoDTO result = dispositivoService.update(dispositivoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dispositivoRepository, times(1)).save(dispositivo);
    }

    @Test
    void testFindAllDispositivos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Dispositivo> page = new PageImpl<>(List.of(dispositivo));
        when(dispositivoRepository.findAll(pageable)).thenReturn(page);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        Page<DispositivoDTO> result = dispositivoService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(dispositivoRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindOneDispositivo() {
        when(dispositivoRepository.findOneWithEagerRelationships(1L)).thenReturn(Optional.of(dispositivo));
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        Optional<DispositivoDTO> result = dispositivoService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(dispositivoRepository, times(1)).findOneWithEagerRelationships(1L);
    }

    @Test
    void testDeleteDispositivo() {
        doNothing().when(dispositivoRepository).deleteById(1L);

        dispositivoService.delete(1L);

        verify(dispositivoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAllNoPag() {
        List<Dispositivo> dispositivos = List.of(dispositivo);
        when(dispositivoRepository.findAll()).thenReturn(dispositivos);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        List<DispositivoDTO> result = dispositivoService.findAllNoPag();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(dispositivoRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithEagerRelationships() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Dispositivo> page = new PageImpl<>(List.of(dispositivo));
        when(dispositivoRepository.findAllWithEagerRelationships(pageable)).thenReturn(page);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        Page<DispositivoDTO> result = dispositivoService.findAllWithEagerRelationships(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(dispositivoRepository, times(1)).findAllWithEagerRelationships(pageable);
    }

    @Test
    void testPartialUpdateDispositivo() {
        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(dispositivoMapper.toDto(dispositivo)).thenReturn(dispositivoDTO);

        Optional<DispositivoDTO> result = dispositivoService.partialUpdate(dispositivoDTO);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(dispositivoRepository, times(1)).findById(1L);
        verify(dispositivoRepository, times(1)).save(dispositivo);
    }
}
