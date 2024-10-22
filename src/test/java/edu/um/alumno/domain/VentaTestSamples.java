package edu.um.alumno.domain;

import edu.um.alumno.service.dto.UserDTO;
import edu.um.alumno.service.dto.VentaDTO;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VentaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Venta getVentaSample1() {
        User user = new User();
        user.setId(1L);
        return new Venta().id(1L).fechaVenta(ZonedDateTime.now()).precioFinal(BigDecimal.valueOf(100)).user(user);
    }

    public static Venta getVentaSample2() {
        User user = new User();
        user.setId(2L);
        return new Venta().id(2L).fechaVenta(ZonedDateTime.now().minusDays(1)).precioFinal(BigDecimal.valueOf(200)).user(user);
    }

    public static Venta getVentaRandomSampleGenerator() {
        User user = new User();
        user.setId(longCount.incrementAndGet());
        return new Venta()
            .id(longCount.incrementAndGet())
            .fechaVenta(ZonedDateTime.now().minusDays(random.nextInt(30)))
            .precioFinal(BigDecimal.valueOf(random.nextDouble() * 1000))
            .user(user);
    }

    public static VentaDTO getVentaDTOSample1() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setId(1L);
        ventaDTO.setFechaVenta(ZonedDateTime.now());
        ventaDTO.setPrecioFinal(BigDecimal.valueOf(100));
        ventaDTO.setUser(userDTO);
        return ventaDTO;
    }

    public static VentaDTO getVentaDTOSample2() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setId(2L);
        ventaDTO.setFechaVenta(ZonedDateTime.now().minusDays(1));
        ventaDTO.setPrecioFinal(BigDecimal.valueOf(200));
        ventaDTO.setUser(userDTO);
        return ventaDTO;
    }
}
