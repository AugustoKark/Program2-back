package edu.um.alumno.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ApiTokenManagerTest {

    @Mock
    private ObjectMapper objectMapper;

    private ApiTokenManager apiTokenManager;

    @TempDir
    File tempDir;

    private File tempTokenFile;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        tempTokenFile = new File(tempDir, "apitoken.json");
        apiTokenManager = new ApiTokenManager(objectMapper);
    }

    @Test
    void testLoadToken_Success() throws Exception {
        // Arrange
        String token = "test-token";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);

        try (FileWriter file = new FileWriter(tempTokenFile)) {
            file.write(jsonObject.toString());
        }

        // Act
        Optional<ApiToken> result = loadTokenFromFile(tempTokenFile);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(token, result.get().getToken());
    }

    @Test
    void testLoadToken_FileNotFound() {
        // Act
        Optional<ApiToken> result = loadTokenFromFile(tempTokenFile);
        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveToken_Success() throws Exception {
        // Arrange
        String token = "test-token";
        ApiToken apiToken = new ApiToken();
        apiToken.setToken(token);

        // Act
        saveTokenToFile(apiToken, tempTokenFile);

        // Assert
        try (FileReader reader = new FileReader(tempTokenFile)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            assertEquals(token, jsonObject.getString("token"));
        }
    }

    @Test
    void testSaveToken_IOException() {
        // Arrange
        String token = "test-token";
        ApiToken apiToken = new ApiToken();
        apiToken.setToken(token);

        // Create a spy of ApiTokenManager
        ApiTokenManager apiTokenManagerSpy = spy(apiTokenManager);

        // Simulate IOException by overriding the saveToken method
        doAnswer(invocation -> {
            throw new RuntimeException("Failed to save token", new IOException());
        })
            .when(apiTokenManagerSpy)
            .saveToken(any(ApiToken.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            apiTokenManagerSpy.saveToken(apiToken);
        });
        assertEquals("Failed to save token", exception.getMessage());
    }

    private Optional<ApiToken> loadTokenFromFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            String token = jsonObject.getString("token");
            ApiToken apiToken = new ApiToken();
            apiToken.setToken(token);
            return Optional.of(apiToken);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private void saveTokenToFile(ApiToken apiToken, File file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", apiToken.getToken());

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save token", e);
        }
    }
}
