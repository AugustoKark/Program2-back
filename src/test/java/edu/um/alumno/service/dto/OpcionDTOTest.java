package edu.um.alumno.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import edu.um.alumno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcionDTO.class);
        OpcionDTO opcionDTO1 = new OpcionDTO();
        opcionDTO1.setId(1L);
        OpcionDTO opcionDTO2 = new OpcionDTO();
        assertThat(opcionDTO1).isNotEqualTo(opcionDTO2);
        opcionDTO2.setId(opcionDTO1.getId());
        assertThat(opcionDTO1).isEqualTo(opcionDTO2);
        opcionDTO2.setId(2L);
        assertThat(opcionDTO1).isNotEqualTo(opcionDTO2);
        opcionDTO1.setId(null);
        assertThat(opcionDTO1).isNotEqualTo(opcionDTO2);
    }
}
