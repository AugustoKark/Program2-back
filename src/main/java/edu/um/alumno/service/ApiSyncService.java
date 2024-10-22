package edu.um.alumno.service;

import edu.um.alumno.domain.ApiToken;
import edu.um.alumno.domain.ApiTokenManager;
import edu.um.alumno.domain.AuthResponse;
import edu.um.alumno.service.dto.DispositivoDTO;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiSyncService {

    private static final Logger LOG = LoggerFactory.getLogger(AdicionalService.class);

    private final ApiTokenManager apiTokenManager;
    private final RestTemplate restTemplate;

    private static final String PROFESSOR_API_URL = "http://192.168.194.254:8080/api";
    private static final String AUTH_URL = PROFESSOR_API_URL + "/authenticate";
    private static final String DEVICES_URL = PROFESSOR_API_URL + "/catedra/dispositivos";

    private static final String USERNAME = "juanperez11";
    private static final String PASSWORD = "juan123";

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    public ApiSyncService(ApiTokenManager apiTokenManager, RestTemplate restTemplate) {
        this.apiTokenManager = apiTokenManager;
        this.restTemplate = restTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        LOG.info("Initializing ApiSyncService");
        syncDataWithRetry();
        startScheduledSync();
    }

    private void syncDataWithRetry() {
        LOG.info("Starting data sync with retry");
        Optional<ApiToken> optionalToken = apiTokenManager.loadToken();
        if (optionalToken.isPresent()) {
            ApiToken token = optionalToken.get();
            try {
                if (!syncData(token.getToken())) {
                    LOG.warn("Token expired, renewing token");
                    token = renewToken();
                    syncData(token.getToken());
                }
            } catch (HttpClientErrorException.Unauthorized e) {
                LOG.error("Unauthorized error: {}", e.getMessage());
                // Log the error and retry token renewal
                System.err.println("Unauthorized error: " + e.getMessage());
                token = renewToken();
                syncData(token.getToken());
            }
        } else {
            LOG.warn("No token found, renewing token");
            ApiToken token = renewToken();
            syncData(token.getToken());
        }
    }

    private boolean syncData(String jwtToken) {
        LOG.info("Syncing data with token: {}", jwtToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<DispositivoDTO>> response = restTemplate.exchange(
            DEVICES_URL,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<DispositivoDTO>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            List<DispositivoDTO> devices = response.getBody();
            LOG.info("Data sync successful, {} devices retrieved", devices.size());
            updateLocalDatabase(devices);
            return true;
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            LOG.warn("Unauthorized access, token may be expired");

            return false;
        } else {
            LOG.error("Failed to sync data, status code: {}", response.getStatusCode());
            throw new RuntimeException("Failed to sync data");
        }
    }

    private ApiToken renewToken() {
        LOG.info("Renewing token");
        Map<String, Object> authRequest = new HashMap<>();
        authRequest.put("username", USERNAME);
        authRequest.put("password", PASSWORD);
        authRequest.put("rememberMe", false);

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(AUTH_URL, authRequest, AuthResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String newToken = response.getBody().getId_token();
            LOG.info("Token renewed successfully: {}", newToken);

            System.out.println(newToken);
            ApiToken apiToken = new ApiToken();
            apiToken.setToken(newToken);
            apiTokenManager.saveToken(apiToken);
            updateTokenFile(newToken);

            return apiToken;
        } else {
            LOG.error("Failed to renew token, status code: {}", response.getStatusCode());
            throw new RuntimeException("Failed to renew token");
        }
    }

    private void updateTokenFile(String newToken) {
        LOG.info("Updating token file with new token");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", newToken);

        try (FileWriter file = new FileWriter("apitoken.json")) {
            file.write(jsonObject.toString());
            LOG.info("Token file updated successfully");
        } catch (IOException e) {
            LOG.error("Failed to update token file", e);
            throw new RuntimeException("Failed to update token file", e);
        }
    }

    private void startScheduledSync() {
        LOG.info("Starting scheduled data sync");
        //        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //        scheduler.scheduleAtFixedRate(this::syncDataWithRetry, 0, 1, TimeUnit.HOURS);
    }

    private void updateLocalDatabase(List<DispositivoDTO> devices) {
        LOG.info("Updating local database with {} devices", devices.size());
        dispositivoService.saveAll(devices);

        LOG.info("Local database updated successfully");
    }
}
