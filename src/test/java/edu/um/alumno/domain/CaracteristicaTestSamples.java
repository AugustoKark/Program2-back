package edu.um.alumno.domain;

import edu.um.alumno.service.dto.CaracteristicaDTO;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CaracteristicaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Caracteristica getCaracteristicaSample1() {
        return new Caracteristica().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Caracteristica getCaracteristicaSample2() {
        return new Caracteristica().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Caracteristica getCaracteristicaRandomSampleGenerator() {
        return new Caracteristica()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }

    public static CaracteristicaDTO getCaracteristicaDTOSample1() {
        CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
        caracteristicaDTO.setId(1L);
        caracteristicaDTO.setNombre("nombre1");
        caracteristicaDTO.setDescripcion("descripcion1");
        return caracteristicaDTO;
    }

    public static CaracteristicaDTO getCaracteristicaDTOSample2() {
        CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
        caracteristicaDTO.setId(2L);
        caracteristicaDTO.setNombre("nombre2");
        caracteristicaDTO.setDescripcion("descripcion2");
        return caracteristicaDTO;
    }
}
