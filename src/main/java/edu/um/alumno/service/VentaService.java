package edu.um.alumno.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.um.alumno.domain.User;
import edu.um.alumno.domain.Venta;
import edu.um.alumno.repository.UserRepository;
import edu.um.alumno.repository.VentaRepository;
import edu.um.alumno.service.dto.VentaDTO;
import edu.um.alumno.service.dto.VentaRequestDTO;
import edu.um.alumno.service.dto.VentaResponseDTO;
import edu.um.alumno.service.mapper.VentaMapper;
import edu.um.alumno.web.rest.VentaResource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service Implementation for managing {@link edu.um.alumno.domain.Venta}.
 */
@Service
@Transactional
public class VentaService {

    private static final Logger LOG = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;
    private final UserRepository userRepository;

    private final VentaMapper ventaMapper;

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String profesorBackendUrl = "http://192.168.194.254:8080/api/catedra/";

    public VentaService(VentaRepository ventaRepository, VentaMapper ventaMapper, UserRepository userRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save.
     * @return the persisted entity.
     */
    public VentaDTO save(VentaDTO ventaDTO) {
        LOG.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    /**
     * Update a venta.
     *
     * @param ventaDTO the entity to save.
     * @return the persisted entity.
     */
    public VentaDTO update(VentaDTO ventaDTO) {
        LOG.debug("Request to update Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    /**
     * Partially update a venta.
     *
     * @param ventaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        LOG.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);

                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VentaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable).map(ventaMapper::toDto);
    }

    /**
     * Get one venta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        LOG.debug("Request to get Venta : {}", id);
        return ventaRepository.findById(id).map(ventaMapper::toDto);
    }

    /**
     * Delete the venta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

    public List<VentaDTO> getVentasByUserId(Long userId) {
        LOG.debug("Request to get all Ventas for user : {}", userId);
        List<Venta> ventas = ventaRepository.findByUserId(userId);
        return ventas.stream().map(ventaMapper::toDto).collect(Collectors.toList());
    }

    public Venta procesarVenta(VentaRequestDTO ventaRequestDTO) {
        String token = getToken();

        // Obtener el id del usuario autenticado
        Long userId = ventaRequestDTO.getUserId();
        System.out.println("----------------------------------------------");

        System.out.println("REQUEST DTO: " + ventaRequestDTO.toString());
        System.out.println("----------------------------------------------");

        // Obtener el usuario desde el repositorio
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Crear los headers y agregar el token JWT
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Crear la entidad HTTP con los headers y el cuerpo de la solicitud
        HttpEntity<VentaRequestDTO> entity = new HttpEntity<>(ventaRequestDTO, headers);

        // Enviar solicitud al backend del profesor
        ResponseEntity<VentaResponseDTO> response = restTemplate.exchange(
            profesorBackendUrl + "/vender",
            HttpMethod.POST,
            entity,
            VentaResponseDTO.class
        );

        System.out.println("----------------------------------------------");

        System.out.println("RESPONSE: " + response.getBody());
        System.out.println("idVenta: " + VentaResponseDTO.getIdVenta());

        System.out.println("----------------------------------------------");

        // Crear y guardar la venta en la base de datos
        Venta venta = new Venta();

        venta.setId(VentaResponseDTO.getIdVenta());
        venta.setFechaVenta(ventaRequestDTO.getFechaVenta());
        venta.setPrecioFinal(ventaRequestDTO.getPrecioFinal());
        venta.setUser(user);

        return ventaRepository.save(venta);
    }

    public static Map<String, Object> getVentaById(Long id) {
        String token = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        System.out.println("----------------------------------------------");
        System.out.println("Pidiendo venta con id: " + id + " al PROFE");
        System.out.println("----------------------------------------------");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            profesorBackendUrl + "/venta/" + id,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }

    public static List<Map<String, Object>> getAllVentasAdmin() {
        String token = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        System.out.println("----------------------------------------------");
        System.out.println("Pidiendo todas las ventas al PROFE SIENDO ADMIN");
        System.out.println("----------------------------------------------");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            profesorBackendUrl + "/ventas",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        return response.getBody();
    }

    private static String getToken() {
        String token = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] jsonData = Files.readAllBytes(Paths.get("apitoken.json"));
            Map<String, String> tokenMap = objectMapper.readValue(jsonData, Map.class);
            token = tokenMap.get("token");
        } catch (IOException e) {
            throw new RuntimeException("Error reading token from apitoken.json", e);
        }
        return token;
    }
}
