package edu.um.alumno.domain;

import static edu.um.alumno.domain.CaracteristicaTestSamples.*;
import static edu.um.alumno.domain.DispositivoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.um.alumno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaracteristicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristica.class);
        Caracteristica caracteristica1 = getCaracteristicaSample1();
        Caracteristica caracteristica2 = new Caracteristica();
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);

        caracteristica2.setId(caracteristica1.getId());
        assertThat(caracteristica1).isEqualTo(caracteristica2);

        caracteristica2 = getCaracteristicaSample2();
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
    }

    @Test
    void dispositivoTest() {
        Caracteristica caracteristica = getCaracteristicaRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        caracteristica.setDispositivo(dispositivoBack);
        assertThat(caracteristica.getDispositivo()).isEqualTo(dispositivoBack);

        caracteristica.dispositivo(null);
        assertThat(caracteristica.getDispositivo()).isNull();
    }
}
