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
import org.springframework.web.client.RestTemplate;

class ApiSyncServiceTest {

    @Mock
    private ApiTokenManager apiTokenManager;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DispositivoService dispositivoService;

    @InjectMocks
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
}
