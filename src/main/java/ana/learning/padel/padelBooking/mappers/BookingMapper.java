package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "bookingOwner.id", target = "bookingOwnerId")
    BookingDTO toDTO(Booking booking);
    @Mapping(source = "bookingOwnerId", target = "bookingOwner.id")
    Booking toBooking(BookingDTO dto);
}

