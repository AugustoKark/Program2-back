package edu.um.alumno.domain;

import edu.um.alumno.service.dto.OpcionDTO;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpcionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opcion getOpcionSample1() {
        return new Opcion().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1").precioAdicional(BigDecimal.valueOf(100));
    }

    public static Opcion getOpcionSample2() {
        return new Opcion().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2").precioAdicional(BigDecimal.valueOf(200));
    }

    public static Opcion getOpcionRandomSampleGenerator() {
        return new Opcion()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .precioAdicional(BigDecimal.valueOf(random.nextDouble() * 1000));
    }

    public static OpcionDTO getOpcionDTOSample1() {
        OpcionDTO opcionDTO = new OpcionDTO();
        opcionDTO.setId(1L);
        opcionDTO.setCodigo("codigo1");
        opcionDTO.setNombre("nombre1");
        opcionDTO.setDescripcion("descripcion1");
        opcionDTO.setPrecioAdicional(BigDecimal.valueOf(100));
        return opcionDTO;
    }

    public static OpcionDTO getOpcionDTOSample2() {
        OpcionDTO opcionDTO = new OpcionDTO();
        opcionDTO.setId(2L);
        opcionDTO.setCodigo("codigo2");
        opcionDTO.setNombre("nombre2");
        opcionDTO.setDescripcion("descripcion2");
        opcionDTO.setPrecioAdicional(BigDecimal.valueOf(200));
        return opcionDTO;
    }
}
