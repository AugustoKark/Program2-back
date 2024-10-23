package edu.um.alumno.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.um.alumno.repository.VentaRepository;
import edu.um.alumno.service.VentaService;
import edu.um.alumno.service.dto.VentaDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VentaResource.class)
@WithMockUser
class VentaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @MockBean
    private VentaRepository ventaRepository;

    private VentaDTO ventaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ventaDTO = new VentaDTO();
        ventaDTO.setId(1L);
    }

    @Test
    void testGetVenta() throws Exception {
        when(ventaService.findOne(1L)).thenReturn(Optional.of(ventaDTO));

        mockMvc.perform(get("/api/ventas/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteVenta() throws Exception {
        doNothing().when(ventaService).delete(1L);

        mockMvc.perform(delete("/api/ventas/1").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(status().isNoContent());
    }
}
