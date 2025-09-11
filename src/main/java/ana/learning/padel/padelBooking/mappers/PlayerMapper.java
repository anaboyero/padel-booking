package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerDTO toDTO(Player player);
    Player toPlayer(PlayerDTO dto);
}
