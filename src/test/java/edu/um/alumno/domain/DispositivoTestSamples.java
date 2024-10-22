package edu.um.alumno.domain;

import edu.um.alumno.service.dto.DispositivoDTO;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DispositivoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dispositivo getDispositivoSample1() {
        return new Dispositivo()
            .id(1L)
            .codigo("codigo1")
            .nombre("nombre1")
            .descripcion("descripcion1")
            .precioBase(BigDecimal.valueOf(100))
            .moneda("USD");
    }

    public static Dispositivo getDispositivoSample2() {
        return new Dispositivo()
            .id(2L)
            .codigo("codigo2")
            .nombre("nombre2")
            .descripcion("descripcion2")
            .precioBase(BigDecimal.valueOf(200))
            .moneda("EUR");
    }

    public static Dispositivo getDispositivoRandomSampleGenerator() {
        return new Dispositivo()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .precioBase(BigDecimal.valueOf(random.nextDouble() * 1000))
            .moneda("USD");
    }

    public static DispositivoDTO getDispositivoDTOSample1() {
        DispositivoDTO dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setId(1L);
        dispositivoDTO.setCodigo("codigo1");
        dispositivoDTO.setNombre("nombre1");
        dispositivoDTO.setDescripcion("descripcion1");
        dispositivoDTO.setPrecioBase(BigDecimal.valueOf(100));
        dispositivoDTO.setMoneda("USD");
        return dispositivoDTO;
    }

    public static DispositivoDTO getDispositivoDTOSample2() {
        DispositivoDTO dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setId(2L);
        dispositivoDTO.setCodigo("codigo2");
        dispositivoDTO.setNombre("nombre2");
        dispositivoDTO.setDescripcion("descripcion2");
        dispositivoDTO.setPrecioBase(BigDecimal.valueOf(200));
        dispositivoDTO.setMoneda("EUR");
        return dispositivoDTO;
    }
}
