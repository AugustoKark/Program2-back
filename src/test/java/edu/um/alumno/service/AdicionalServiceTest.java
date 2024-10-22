package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Adicional;
import edu.um.alumno.repository.AdicionalRepository;
import edu.um.alumno.service.dto.AdicionalDTO;
import edu.um.alumno.service.mapper.AdicionalMapper;
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

class AdicionalServiceTest {

    @Mock
    private AdicionalRepository adicionalRepository;

    @Mock
    private AdicionalMapper adicionalMapper;

    @InjectMocks
    private AdicionalService adicionalService;

    private Adicional adicional;
    private AdicionalDTO adicionalDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adicional = new Adicional();
        adicional.setId(1L);
        adicional.setNombre("Adicional 1");
        adicional.setDescripcion("Descripción del adicional 1");
        adicional.setPrecio(new BigDecimal("100.00"));

        adicionalDTO = new AdicionalDTO();
        adicionalDTO.setId(1L);
        adicionalDTO.setNombre("Adicional 1");
        adicionalDTO.setDescripcion("Descripción del adicional 1");
        adicionalDTO.setPrecio(new BigDecimal("100.00"));
    }

    @Test
    void testSave() {
        when(adicionalMapper.toEntity(any(AdicionalDTO.class))).thenReturn(adicional);
        when(adicionalRepository.save(any(Adicional.class))).thenReturn(adicional);
        when(adicionalMapper.toDto(any(Adicional.class))).thenReturn(adicionalDTO);

        AdicionalDTO result = adicionalService.save(adicionalDTO);

        assertNotNull(result);
        assertEquals(adicionalDTO.getId(), result.getId());
        verify(adicionalRepository, times(1)).save(any(Adicional.class));
    }

    @Test
    void testUpdate() {
        when(adicionalMapper.toEntity(any(AdicionalDTO.class))).thenReturn(adicional);
        when(adicionalRepository.save(any(Adicional.class))).thenReturn(adicional);
        when(adicionalMapper.toDto(any(Adicional.class))).thenReturn(adicionalDTO);

        AdicionalDTO result = adicionalService.update(adicionalDTO);

        assertNotNull(result);
        assertEquals(adicionalDTO.getId(), result.getId());
        verify(adicionalRepository, times(1)).save(any(Adicional.class));
    }

    @Test
    void testPartialUpdate() {
        when(adicionalRepository.findById(anyLong())).thenReturn(Optional.of(adicional));
        when(adicionalRepository.save(any(Adicional.class))).thenReturn(adicional);
        when(adicionalMapper.toDto(any(Adicional.class))).thenReturn(adicionalDTO);

        Optional<AdicionalDTO> result = adicionalService.partialUpdate(adicionalDTO);

        assertTrue(result.isPresent());
        assertEquals(adicionalDTO.getId(), result.get().getId());
        verify(adicionalRepository, times(1)).save(any(Adicional.class));
    }

    @Test
    void testFindAll() {
        Page<Adicional> page = new PageImpl<>(Collections.singletonList(adicional));
        when(adicionalRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(adicionalMapper.toDto(any(Adicional.class))).thenReturn(adicionalDTO);

        Page<AdicionalDTO> result = adicionalService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(adicionalRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindOne() {
        when(adicionalRepository.findById(anyLong())).thenReturn(Optional.of(adicional));
        when(adicionalMapper.toDto(any(Adicional.class))).thenReturn(adicionalDTO);

        Optional<AdicionalDTO> result = adicionalService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(adicionalDTO.getId(), result.get().getId());
        verify(adicionalRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        doNothing().when(adicionalRepository).deleteById(anyLong());

        adicionalService.delete(1L);

        verify(adicionalRepository, times(1)).deleteById(anyLong());
    }
}
