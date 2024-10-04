package edu.um.alumno.domain;

import static edu.um.alumno.domain.OpcionTestSamples.*;
import static edu.um.alumno.domain.PersonalizacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.um.alumno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opcion.class);
        Opcion opcion1 = getOpcionSample1();
        Opcion opcion2 = new Opcion();
        assertThat(opcion1).isNotEqualTo(opcion2);

        opcion2.setId(opcion1.getId());
        assertThat(opcion1).isEqualTo(opcion2);

        opcion2 = getOpcionSample2();
        assertThat(opcion1).isNotEqualTo(opcion2);
    }

    @Test
    void personalizacionTest() {
        Opcion opcion = getOpcionRandomSampleGenerator();
        Personalizacion personalizacionBack = getPersonalizacionRandomSampleGenerator();

        opcion.setPersonalizacion(personalizacionBack);
        assertThat(opcion.getPersonalizacion()).isEqualTo(personalizacionBack);

        opcion.personalizacion(null);
        assertThat(opcion.getPersonalizacion()).isNull();
    }
}