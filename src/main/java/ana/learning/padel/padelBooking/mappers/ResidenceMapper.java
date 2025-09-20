package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
import ana.learning.padel.padelBooking.model.Residence;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResidenceMapper {

    ResidenceMapper INSTANCE = Mappers.getMapper(ResidenceMapper.class);
    ResidenceDTO toDTO(Residence residence);
    Residence toResidence(ResidenceDTO dto);
}
