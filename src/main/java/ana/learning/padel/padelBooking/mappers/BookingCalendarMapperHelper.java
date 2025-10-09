package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingCalendarMapperHelper {

    private final BookingMapper bookingMapper;

    public BookingCalendarMapperHelper(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    public BookingCalendarDTO customToDTO(BookingCalendar calendar) {
        BookingCalendarDTO dto = new BookingCalendarDTO();
        dto.setId(calendar.getId());
        dto.setStartDay(calendar.getStartDay());

        calendar.getAvailableBookings().size(); // Force loading of available bookings
        calendar.getReservedBookings().size(); // Force loading of available bookings

        List<BookingDTO> availableBookings = calendar.getAvailableBookings().stream()
                .map(bookingMapper::toDTO)
                .toList();

        List<BookingDTO> reservedBookings = calendar.getReservedBookings().stream()
                .map(bookingMapper::toDTO)
                .toList();

        dto.setAvailableBookings(availableBookings);
        dto.setReservedBookings(reservedBookings);
        return dto;
    }
}
