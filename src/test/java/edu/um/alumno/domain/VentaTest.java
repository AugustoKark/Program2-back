package edu.um.alumno.domain;

import static edu.um.alumno.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.um.alumno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = getVentaSample1();
        Venta venta2 = new Venta();
        assertThat(venta1).isNotEqualTo(venta2);

        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);

        venta2 = getVentaSample2();
        assertThat(venta1).isNotEqualTo(venta2);
    }
}
