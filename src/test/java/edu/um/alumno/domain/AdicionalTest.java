package edu.um.alumno.domain;

import static edu.um.alumno.domain.AdicionalTestSamples.*;
import static edu.um.alumno.domain.DispositivoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.um.alumno.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdicionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adicional.class);
        Adicional adicional1 = getAdicionalSample1();
        Adicional adicional2 = new Adicional();
        assertThat(adicional1).isNotEqualTo(adicional2);

        adicional2.setId(adicional1.getId());
        assertThat(adicional1).isEqualTo(adicional2);

        adicional2 = getAdicionalSample2();
        assertThat(adicional1).isNotEqualTo(adicional2);
    }

    @Test
    void dispositivosTest() {
        Adicional adicional = getAdicionalRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        adicional.addDispositivos(dispositivoBack);
        assertThat(adicional.getDispositivos()).containsOnly(dispositivoBack);
        assertThat(dispositivoBack.getAdicionales()).containsOnly(adicional);

        adicional.removeDispositivos(dispositivoBack);
        assertThat(adicional.getDispositivos()).doesNotContain(dispositivoBack);
        assertThat(dispositivoBack.getAdicionales()).doesNotContain(adicional);

        adicional.dispositivos(new HashSet<>(Set.of(dispositivoBack)));
        assertThat(adicional.getDispositivos()).containsOnly(dispositivoBack);
        assertThat(dispositivoBack.getAdicionales()).containsOnly(adicional);

        adicional.setDispositivos(new HashSet<>());
        assertThat(adicional.getDispositivos()).doesNotContain(dispositivoBack);
        assertThat(dispositivoBack.getAdicionales()).doesNotContain(adicional);
    }
}
