package edu.um.alumno.domain;

import edu.um.alumno.service.dto.PersonalizacionDTO;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonalizacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Personalizacion getPersonalizacionSample1() {
        return new Personalizacion().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Personalizacion getPersonalizacionSample2() {
        return new Personalizacion().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Personalizacion getPersonalizacionRandomSampleGenerator() {
        return new Personalizacion()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }

    public static PersonalizacionDTO getPersonalizacionDTOSample1() {
        PersonalizacionDTO personalizacionDTO = new PersonalizacionDTO();
        personalizacionDTO.setId(1L);
        personalizacionDTO.setNombre("nombre1");
        personalizacionDTO.setDescripcion("descripcion1");
        return personalizacionDTO;
    }

    public static PersonalizacionDTO getPersonalizacionDTOSample2() {
        PersonalizacionDTO personalizacionDTO = new PersonalizacionDTO();
        personalizacionDTO.setId(2L);
        personalizacionDTO.setNombre("nombre2");
        personalizacionDTO.setDescripcion("descripcion2");
        return personalizacionDTO;
    }
}
