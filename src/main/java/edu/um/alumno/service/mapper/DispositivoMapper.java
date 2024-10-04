package edu.um.alumno.service.mapper;

import edu.um.alumno.domain.Adicional;
import edu.um.alumno.domain.Dispositivo;
import edu.um.alumno.service.dto.AdicionalDTO;
import edu.um.alumno.service.dto.DispositivoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispositivo} and its DTO {@link DispositivoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositivoMapper extends EntityMapper<DispositivoDTO, Dispositivo> {
    @Mapping(target = "adicionales", source = "adicionales", qualifiedByName = "adicionalIdSet")
    DispositivoDTO toDto(Dispositivo s);

    @Mapping(target = "removeAdicionales", ignore = true)
    Dispositivo toEntity(DispositivoDTO dispositivoDTO);

    @Named("adicionalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdicionalDTO toDtoAdicionalId(Adicional adicional);

    @Named("adicionalIdSet")
    default Set<AdicionalDTO> toDtoAdicionalIdSet(Set<Adicional> adicional) {
        return adicional.stream().map(this::toDtoAdicionalId).collect(Collectors.toSet());
    }
}
