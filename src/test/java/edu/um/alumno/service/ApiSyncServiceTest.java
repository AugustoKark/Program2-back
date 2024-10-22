package edu.um.alumno.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.um.alumno.domain.ApiToken;
import edu.um.alumno.domain.ApiTokenManager;
import edu.um.alumno.domain.AuthResponse;
import edu.um.alumno.service.dto.DispositivoDTO;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

class ApiSyncServiceTest {

    @Mock
    private ApiTokenManager apiTokenManager;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DispositivoService dispositivoService;

    private ApiSyncService apiSyncService;

    private ApiToken apiToken;
    private DispositivoDTO dispositivoDTO;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        apiToken = new ApiToken();
        apiToken.setToken("test-token");

        dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setId(1L);

        authResponse = new AuthResponse();
        authResponse.setId_token("new-test-token");

        // Manually create the spy instance
        apiSyncService = spy(new ApiSyncService(apiTokenManager, restTemplate));
        apiSyncService.dispositivoService = dispositivoService;

        // Mock the updateTokenFile method to do nothing
        doNothing().when(apiSyncService).updateTokenFile(anyString());
    }

    @Test
    void testRenewToken() {
        when(restTemplate.postForEntity(anyString(), any(), eq(AuthResponse.class))).thenReturn(
            new ResponseEntity<>(authResponse, HttpStatus.OK)
        );

        ApiToken newToken = apiSyncService.renewToken();

        assertNotNull(newToken);
        assertEquals("new-test-token", newToken.getToken());
        verify(apiTokenManager, times(1)).saveToken(any(ApiToken.class));
    }

    @Test
    void testSyncDataWithRetry_ValidToken() {
        when(apiTokenManager.loadToken()).thenReturn(Optional.of(apiToken));
        doReturn(true).when(apiSyncService).syncData(anyString());

        apiSyncService.syncDataWithRetry();

        verify(apiSyncService, times(1)).syncData("test-token");
    }

    @Test
    void testSyncDataWithRetry_InvalidToken() {
        when(apiTokenManager.loadToken()).thenReturn(Optional.of(apiToken));
        doReturn(false).when(apiSyncService).syncData(anyString());
        doReturn(apiToken).when(apiSyncService).renewToken();
        doReturn(true).when(apiSyncService).syncData("new-test-token");

        apiSyncService.syncDataWithRetry();

        verify(apiSyncService, times(2)).syncData("test-token");
        verify(apiSyncService, times(1)).renewToken();
    }

    @Test
    void testSyncData_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("test-token");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<DispositivoDTO> dispositivos = Collections.singletonList(dispositivoDTO);
        ResponseEntity<List<DispositivoDTO>> response = new ResponseEntity<>(dispositivos, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(entity), any(ParameterizedTypeReference.class))).thenReturn(
            response
        );

        boolean result = apiSyncService.syncData("test-token");

        assertTrue(result);
        verify(apiSyncService, times(1)).updateLocalDatabase(dispositivos);
    }

    @Test
    void testUpdateLocalDatabase() {
        List<DispositivoDTO> remoteDevices = Collections.singletonList(dispositivoDTO);
        when(dispositivoService.findAllNoPag()).thenReturn(Collections.emptyList());

        apiSyncService.updateLocalDatabase(remoteDevices);

        verify(dispositivoService, times(1)).save(dispositivoDTO);
    }
}
