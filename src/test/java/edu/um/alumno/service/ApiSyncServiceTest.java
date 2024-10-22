package edu.um.alumno.service;

//
//import edu.um.alumno.domain.ApiToken;
//import edu.um.alumno.domain.ApiTokenManager;
//import edu.um.alumno.domain.AuthResponse;
//import edu.um.alumno.service.dto.DispositivoDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
class ApiSyncServiceTest {
    //
    //    @Mock
    //    private ApiTokenManager apiTokenManager;
    //
    //    @Mock
    //    private RestTemplate restTemplate;
    //
    //    @Mock
    //    private DispositivoService dispositivoService;
    //
    //    @InjectMocks
    //    private ApiSyncService apiSyncService;
    //
    //    private ApiToken validToken;
    //    private AuthResponse authResponse;
    //
    //    @BeforeEach
    //    void setUp() {
    //        MockitoAnnotations.openMocks(this);
    //
    //        // Configuración de datos de prueba
    //        validToken = new ApiToken();
    //        validToken.setToken("validToken123");
    //
    //        authResponse = new AuthResponse();
    //        authResponse.setId_token("newToken456");
    //    }
    //
    //    @Test
    //    void testSyncDataWithRetry_ExpiredToken() {
    //        // Configurar mocks para simular un token expirado y luego renovarlo
    //        when(apiTokenManager.loadToken()).thenReturn(Optional.of(validToken));
    //        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
    //            .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
    //        when(restTemplate.postForEntity(anyString(), any(), eq(AuthResponse.class)))
    //            .thenReturn(new ResponseEntity<>(authResponse, HttpStatus.OK));
    //
    //        // Simular una segunda llamada exitosa con el nuevo token
    //        List<DispositivoDTO> dispositivos = Arrays.asList(
    //            createDispositivo(1L, "D001", "Dispositivo 1")
    //        );
    //        ResponseEntity<List<DispositivoDTO>> responseEntity = new ResponseEntity<>(dispositivos, HttpStatus.OK);
    //        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
    //            .thenReturn(responseEntity);
    //
    //        // Llamar al método
    //        apiSyncService.syncDataWithRetry();
    //
    //        // Verificar que se renovó el token
    //        verify(apiTokenManager, times(1)).saveToken(any(ApiToken.class));
    //        verify(dispositivoService, times(1)).save(any(DispositivoDTO.class));
    //    }
    //
    //    @Test
    //    void testRenewToken_Success() {
    //        // Simular una respuesta exitosa de autenticación
    //        when(restTemplate.postForEntity(anyString(), any(), eq(AuthResponse.class)))
    //            .thenReturn(new ResponseEntity<>(authResponse, HttpStatus.OK));
    //
    //        // Llamar al método
    //        ApiToken newToken = apiSyncService.renewToken();
    //
    //        // Verificar que el nuevo token fue guardado
    //        verify(apiTokenManager, times(1)).saveToken(newToken);
    //        assertEquals("newToken456", newToken.getToken());
    //    }
    //
    //    @Test
    //    void testSyncDataWithRetry_ValidToken() {
    //        // Configurar mocks para retornar un token válido
    //        when(apiTokenManager.loadToken()).thenReturn(Optional.of(validToken));
    //
    //        // Simular la respuesta del backend con una lista de dispositivos
    //        List<DispositivoDTO> dispositivos = Arrays.asList(
    //            createDispositivo(1L, "NTB01", "Lenovo IdeaPad 1 Laptop"),
    //            createDispositivo(2L, "NTB02", "MSI Stealth 18 AI Studio")
    //        );
    //
    //        ResponseEntity<List<DispositivoDTO>> responseEntity = new ResponseEntity<>(dispositivos, HttpStatus.OK);
    //        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
    //            .thenReturn(responseEntity);
    //
    //        // Llamar al método
    //        apiSyncService.syncDataWithRetry();
    //
    //        // Verificar que se actualizó la base de datos local
    //        verify(dispositivoService, times(2)).save(any(DispositivoDTO.class));
    //    }
    //
    //    // Método auxiliar para crear un dispositivo
    //    private DispositivoDTO createDispositivo(Long id, String codigo, String nombre) {
    //        DispositivoDTO dispositivo = new DispositivoDTO();
    //        dispositivo.setId(id);
    //        dispositivo.setCodigo(codigo);
    //        dispositivo.setNombre(nombre);
    //        dispositivo.setDescripcion("Descripción");
    //        dispositivo.setPrecioBase(new BigDecimal("100.00"));
    //        dispositivo.setMoneda("USD");
    //        return dispositivo;
    //    }
}
