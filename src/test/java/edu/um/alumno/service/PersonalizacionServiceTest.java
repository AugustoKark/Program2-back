package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.Personalizacion;
import edu.um.alumno.repository.PersonalizacionRepository;
import edu.um.alumno.service.dto.PersonalizacionDTO;
import edu.um.alumno.service.mapper.PersonalizacionMapper;
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

class PersonalizacionServiceTest {

    @Mock
    private PersonalizacionRepository personalizacionRepository;

    @Mock
    private PersonalizacionMapper personalizacionMapper;

    @InjectMocks
    private PersonalizacionService personalizacionService;

    private Personalizacion personalizacion;
    private PersonalizacionDTO personalizacionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        personalizacion = new Personalizacion();
        personalizacion.setId(1L);
        personalizacion.setNombre("Personalizacion 1");
        personalizacion.setDescripcion("Descripción de la personalizacion 1");

        personalizacionDTO = new PersonalizacionDTO();
        personalizacionDTO.setId(1L);
        personalizacionDTO.setNombre("Personalizacion 1");
        personalizacionDTO.setDescripcion("Descripción de la personalizacion 1");
    }

    @Test
    void testSave() {
        when(personalizacionMapper.toEntity(any(PersonalizacionDTO.class))).thenReturn(personalizacion);
        when(personalizacionRepository.save(any(Personalizacion.class))).thenReturn(personalizacion);
        when(personalizacionMapper.toDto(any(Personalizacion.class))).thenReturn(personalizacionDTO);

        PersonalizacionDTO result = personalizacionService.save(personalizacionDTO);

        assertNotNull(result);
        assertEquals(personalizacionDTO.getId(), result.getId());
        verify(personalizacionRepository, times(1)).save(any(Personalizacion.class));
    }

    @Test
    void testUpdate() {
        when(personalizacionMapper.toEntity(any(PersonalizacionDTO.class))).thenReturn(personalizacion);
        when(personalizacionRepository.save(any(Personalizacion.class))).thenReturn(personalizacion);
        when(personalizacionMapper.toDto(any(Personalizacion.class))).thenReturn(personalizacionDTO);

        PersonalizacionDTO result = personalizacionService.update(personalizacionDTO);

        assertNotNull(result);
        assertEquals(personalizacionDTO.getId(), result.getId());
        verify(personalizacionRepository, times(1)).save(any(Personalizacion.class));
    }

    @Test
    void testPartialUpdate() {
        when(personalizacionRepository.findById(anyLong())).thenReturn(Optional.of(personalizacion));
        when(personalizacionRepository.save(any(Personalizacion.class))).thenReturn(personalizacion);
        when(personalizacionMapper.toDto(any(Personalizacion.class))).thenReturn(personalizacionDTO);

        Optional<PersonalizacionDTO> result = personalizacionService.partialUpdate(personalizacionDTO);

        assertTrue(result.isPresent());
        assertEquals(personalizacionDTO.getId(), result.get().getId());
        verify(personalizacionRepository, times(1)).save(any(Personalizacion.class));
    }

    @Test
    void testFindAll() {
        Page<Personalizacion> page = new PageImpl<>(Collections.singletonList(personalizacion));
        when(personalizacionRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(personalizacionMapper.toDto(any(Personalizacion.class))).thenReturn(personalizacionDTO);

        Page<PersonalizacionDTO> result = personalizacionService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(personalizacionRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testFindOne() {
        when(personalizacionRepository.findById(anyLong())).thenReturn(Optional.of(personalizacion));
        when(personalizacionMapper.toDto(any(Personalizacion.class))).thenReturn(personalizacionDTO);

        Optional<PersonalizacionDTO> result = personalizacionService.findOne(1L);

        assertTrue(result.isPresent());
        assertEquals(personalizacionDTO.getId(), result.get().getId());
        verify(personalizacionRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        doNothing().when(personalizacionRepository).deleteById(anyLong());

        personalizacionService.delete(1L);

        verify(personalizacionRepository, times(1)).deleteById(anyLong());
    }
}
