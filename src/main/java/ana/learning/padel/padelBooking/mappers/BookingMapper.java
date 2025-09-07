package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PlayerMapper.class })
public interface BookingMapper {

    BookingDTO toDTO(Booking booking);
    Booking toBooking(BookingDTO dto);
}
//  PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

