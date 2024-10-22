package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.User;
import edu.um.alumno.domain.Venta;
import edu.um.alumno.repository.UserRepository;
import edu.um.alumno.repository.VentaRepository;
import edu.um.alumno.service.dto.VentaDTO;
import edu.um.alumno.service.dto.VentaRequestDTO;
import edu.um.alumno.service.dto.VentaResponseDTO;
import edu.um.alumno.service.mapper.VentaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private VentaMapper ventaMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

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
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
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

    @Test
    void testFindOneNotFound() {
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<VentaDTO> result = ventaService.findOne(1L);

        assertFalse(result.isPresent());
        verify(ventaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateNotFound() {
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.empty());
        VentaDTO result = ventaService.update(ventaDTO);
        assertNull(result);
        verify(ventaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetVentasByUserId() {
        when(ventaRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(venta));
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaDTO);

        List<VentaDTO> result = ventaService.getVentasByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ventaDTO.getId(), result.get(0).getId());
        verify(ventaRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    void testGetVentaById() {
        Map<String, Object> mockResponse = Map.of(
            "idVenta",
            1520L,
            "idDispositivo",
            1L,
            "codigo",
            "NTB01",
            "nombre",
            "Lenovo IdeaPad 1 Laptop",
            "descripcion",
            "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
            "precioBase",
            718.5,
            "moneda",
            "USD",
            "catacteristicas",
            List.of(
                Map.of("id", 1L, "nombre", "Pantalla", "descripcion", "15.6” FHD Display"),
                Map.of("id", 2L, "nombre", "Camara", "descripcion", "720p Camera w/Privacy Shutter"),
                Map.of("id", 3L, "nombre", "Batería", "descripcion", "Batería 43Wh")
            ),
            "personalizaciones",
            List.of(
                Map.of(
                    "id",
                    1L,
                    "nombre",
                    "CPU",
                    "descripcion",
                    "Procesadores Disponibles",
                    "opcion",
                    Map.of(
                        "id",
                        1L,
                        "codigo",
                        "",
                        "nombre",
                        "Ryzen 5 5500U",
                        "descripcion",
                        "Procesador 1.8 GHz - 6(12) Cores",
                        "precioAdicional",
                        0.0
                    )
                ),
                Map.of(
                    "id",
                    6L,
                    "nombre",
                    "Video",
                    "descripcion",
                    "Video Disponible",
                    "opcion",
                    Map.of("id", 6L, "codigo", "", "nombre", "DDR4-16", "descripcion", "Memoria DDR4 - 16GB", "precioAdicional", 0.0)
                )
            ),
            "adicionales",
            List.of(
                Map.of("id", 1L, "nombre", "Mouse", "descripcion", "Mouse Bluetooth 3 teclas", "precio", 40.5),
                Map.of("id", 2L, "nombre", "Teclado", "descripcion", "Teclado bluetooth", "precio", 78.0)
            )
        );

        when(
            restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class))
        ).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        Map<String, Object> result = VentaService.getVentaById(1520L);

        assertNotNull(result);
        assertEquals(1520L, ((Number) result.get("idVenta")).longValue());
    }

    @Test
    void testGetAllVentasAdmin() {
        List<Map<String, Object>> mockResponse = List.of(
            Map.of(
                "idVenta",
                1520L,
                "id",
                1520L,
                "codigo",
                "NTB01",
                "nombre",
                "Lenovo IdeaPad 1 Laptop",
                "description",
                "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
                "precio",
                718.50
            ),
            Map.of(
                "idVenta",
                1521L,
                "id",
                1521L,
                "codigo",
                "NTB01",
                "nombre",
                "Lenovo IdeaPad 1 Laptop",
                "description",
                "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
                "precio",
                718.50
            ),
            Map.of(
                "idVenta",
                1524L,
                "id",
                1524L,
                "codigo",
                "NTB01",
                "nombre",
                "Lenovo IdeaPad 1 Laptop",
                "description",
                "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
                "precio",
                718.50
            ),
            Map.of(
                "idVenta",
                1525L,
                "id",
                1525L,
                "codigo",
                "NTB01",
                "nombre",
                "Lenovo IdeaPad 1 Laptop",
                "description",
                "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
                "precio",
                718.50
            )
        );
        when(
            restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class))
        ).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<Map<String, Object>> result = ventaService.getAllVentasAdmin();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(1520L, ((Number) result.get(0).get("id")).longValue());
    }
}
