package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.model.Booking;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")
@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setTimeSlot(booking.getTimeSlot());
        if (booking.getBookingOwner()==null) {
            dto.setBookingOwnerId(null);
        }
        else {
        dto.setBookingOwnerId(booking.getBookingOwner().getId());
        }
        if (booking.getCalendar()==null) {
            dto.setCalendarId(null);
        }
        else {
        dto.setCalendarId(booking.getCalendar().getId());
        }
        return dto;
    }

}
